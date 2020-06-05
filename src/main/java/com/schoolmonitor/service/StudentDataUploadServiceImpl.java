
package com.schoolmonitor.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.schoolmonitor.entities.schoolmonitor.Address;
import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.entities.schoolmonitor.Schoolspecific;
import com.schoolmonitor.entities.schoolmonitor.Student;
import com.schoolmonitor.entities.schools.School;
import com.schoolmonitor.entities.schools.Subscription;
import com.schoolmonitor.model.AddressDTO;
import com.schoolmonitor.model.CredentialDTO;
import com.schoolmonitor.model.SchoolSpecificDTO;
import com.schoolmonitor.model.StudentDTO;
import com.schoolmonitor.repositories.schoolmonitor.AddressRepository;
import com.schoolmonitor.repositories.schoolmonitor.CredentialsRepository;
import com.schoolmonitor.repositories.schoolmonitor.SchoolSpecificesRepository;
import com.schoolmonitor.repositories.schoolmonitor.StudentRepository;
import com.schoolmonitor.repositories.schools.SchoolRepository;
import com.schoolmonitor.repositories.schools.SubscriptionRepository;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2019
 * 
 *          Here, Bulk upload will happen for new students only(new/old
 *          Subscription), each upload will be for a particular class/section of a logged in school
 */
@Service
public class StudentDataUploadServiceImpl implements StudentDataUploadService {
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	CredentialsRepository credentialsRepository;
	@Autowired
	SchoolSpecificesRepository schoolSpecificesRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SchoolSpecificDTO schoolSpecificDTO;

	StudentDTO studentDTO;
	AddressDTO addressDTO;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	CredentialDTO credentialDTO;
	@Override
	public Void studentDataUpload(MultipartFile studentDataFile, HttpServletRequest request)
			throws IOException, InvalidFormatException {

		InputStream studentDataFileInputStream = studentDataFile.getInputStream();

		String currentDirectoryAbsolutePath = new File(".").getAbsolutePath();
		String uploadFilePath = currentDirectoryAbsolutePath.substring(0, currentDirectoryAbsolutePath.length() - 1)
				+ request.getSession().getAttribute("Domain") + "\\" + studentDataFile.getOriginalFilename();
		File schoolDir = new File(currentDirectoryAbsolutePath.substring(0, currentDirectoryAbsolutePath.length() - 1)
				+ request.getSession().getAttribute("Domain"));
		if (!schoolDir.isDirectory()) {
			schoolDir.mkdir();
		}
		File fileToUpload = new File(schoolDir, studentDataFile.getOriginalFilename());
		if (fileToUpload.exists())
			fileToUpload.delete();
		FileOutputStream studentDataFileOutputStream = new FileOutputStream(fileToUpload);

		int streamedCharacter = 0;

		while ((streamedCharacter = studentDataFileInputStream.read()) != -1) {
			studentDataFileOutputStream.write(streamedCharacter);
		}
		studentDataFileOutputStream.flush();
		studentDataFileOutputStream.close();

		migrateStudentDataToDB(uploadFilePath, request);
		fileToUpload.delete();

		return null;

	}

	private void migrateStudentDataToDB(String uploadFilePath, HttpServletRequest request)
			throws InvalidFormatException, IOException {
		FileInputStream uploadedFileInputStream = new FileInputStream(uploadFilePath);
		String fileType = uploadFilePath.substring(uploadFilePath.lastIndexOf(".") + 1, uploadFilePath.length());
		Workbook uploadedWorkbook = null;
		Sheet uploadedSheet = null;
		if ("xlsx".equals(fileType)) {
			uploadedWorkbook = new XSSFWorkbook(uploadedFileInputStream);
			uploadedSheet = (XSSFSheet) uploadedWorkbook.getSheetAt(0);
		} else {
			uploadedWorkbook = new HSSFWorkbook(uploadedFileInputStream);
			uploadedSheet = (HSSFSheet) uploadedWorkbook.getSheetAt(0);
		}
		School school = schoolRepository.findByDomainForLogin((String) request.getSession().getAttribute("Domain"));
		if (null != school) {
			Date currentDate = new Date();
			int subscriptionId = school.getSubscriptionId();
			Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
			Date subscribedToDate = subscription.getSubscribedTo();
			Date subscribedFromDate = subscription.getSubscribedFrom();
			Credential loggedIncredential = credentialsRepository
					.findByUserName(((String) request.getSession().getAttribute("Username")));
			Map<String, Integer> columnHeadersMap = new HashMap<String, Integer>();
			// check for valid subscription and Admin rights
			if (loggedIncredential.getIsAdmin() == 1 && currentDate.compareTo(subscribedToDate) <= 0
					&& currentDate.compareTo(subscribedFromDate) >= 0) {
				Iterator<Row> rowIterator = uploadedSheet.rowIterator();
				int schooSpecificsId = 0;
				Schoolspecific schoolSpecific = new Schoolspecific();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					String classRollnumberSectionInformation = "";
					if (row.getRowNum() == 0) {
						Iterator<Cell> cellIterator = row.cellIterator();

						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							columnHeadersMap.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
						}
					} else {
						String studentFistName = "", studentLastName = "";
						Date dateOfBirth = null;

						studentDTO = new StudentDTO();
						credentialDTO = new CredentialDTO();
						addressDTO = new AddressDTO();

						Cell currentCellMarker = null;
						
						if (row.getRowNum() == 1) {
							if (null != row.getCell(columnHeadersMap.get("schoolBranchName".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolBranchName".trim()));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
									schoolSpecificDTO.setBranchName(currentCellMarker.getStringCellValue());
							}
							if (null != row.getCell(columnHeadersMap.get("schoolDistrict".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolDistrict".trim()));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
									schoolSpecificDTO.setDistrict(currentCellMarker.getStringCellValue());
							}
							if (null != row.getCell(columnHeadersMap.get("schoolPincode".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolPincode".trim()));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
									schoolSpecificDTO.setPincode(
											Integer.toString((int) currentCellMarker.getNumericCellValue()));
							}
							if (null != row.getCell(columnHeadersMap.get("schoolAddress".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolAddress".trim()));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
									schoolSpecificDTO.setSchoolAddress(currentCellMarker.getStringCellValue());
							}
							if (null != row.getCell(columnHeadersMap.get("schoolContactNumber".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolContactNumber"));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
									schoolSpecificDTO
											.setSchoolContactNumber((int) currentCellMarker.getNumericCellValue());
							}
							if (null != row.getCell(columnHeadersMap.get("schoolEmailId".trim()))) {
								currentCellMarker = row.getCell(columnHeadersMap.get("schoolEmailId".trim()));
								if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
									schoolSpecificDTO.setSchoolEmailId(currentCellMarker.getStringCellValue());
							}
							BeanUtils.copyProperties(schoolSpecificDTO, schoolSpecific);
							schoolSpecificesRepository.save(schoolSpecific);
							schooSpecificsId = schoolSpecific.getSchoolSpecificsId();

						}

						if (null != row.getCell(columnHeadersMap.get("studentFirstName".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentFirstName".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING) {
								studentFistName = currentCellMarker.getStringCellValue();
								studentDTO.setFirstName(studentFistName);
							}
						}
						if (null != row.getCell(columnHeadersMap.get("studentLastName".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentLastName".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING) {
								studentLastName = currentCellMarker.getStringCellValue();
								studentDTO.setLastName(studentLastName);
							}
						}

						studentDTO.setSchoolId(school.getSchoolId());

						if (null != row.getCell(columnHeadersMap.get("studentStream".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentStream".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								studentDTO.setStream(currentCellMarker.getStringCellValue());
						}

						if (null != row.getCell(columnHeadersMap.get("studentFatherName".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentFatherName".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								studentDTO.setFatherName(currentCellMarker.getStringCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentMotherName".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentMotherName".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								studentDTO.setMotherName(currentCellMarker.getStringCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentBloodGroup".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentBloodGroup".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								studentDTO.setBloodGroup(currentCellMarker.getStringCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentContactNumber".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentContactNumber".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
								studentDTO.setContactNumber((int) currentCellMarker.getNumericCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentEmailID".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentEmailID".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								studentDTO.setStudentEmailId(currentCellMarker.getStringCellValue());
						}

						 studentDTO.setStudentId(studentRepository.findMaxStudentId() + 1);
						 addressDTO.setLinkedStudentId(Integer.toString(studentDTO.getStudentId()));

						if (null != row.getCell(columnHeadersMap.get("studentAddressLandMark".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentAddressLandMark".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								addressDTO.setLandmark(currentCellMarker.getStringCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentAddressCity".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentAddressCity".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								addressDTO.setCity(currentCellMarker.getStringCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentAddressPincode".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentAddressPincode".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
								addressDTO.setPincode(Integer.toString((int) currentCellMarker.getNumericCellValue()));
						}
						Address address = new Address();
						BeanUtils.copyProperties(addressDTO, address);
						addressRepository.save(address);

						if (null != row.getCell(columnHeadersMap.get("studentDOB".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentDOB".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC
									&& HSSFDateUtil.isCellDateFormatted(currentCellMarker)) {
								dateOfBirth = currentCellMarker.getDateCellValue();
								studentDTO.setDateOfBirth(dateOfBirth);
							}
						}

						studentDTO.setSchoolSpecificsId(schoolSpecificDTO.getSchoolSpecificsId());

						if (null != row.getCell(columnHeadersMap.get("studentClass".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentClass".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_STRING)
								classRollnumberSectionInformation += currentCellMarker.getStringCellValue();
						}
						if (null != row.getCell(columnHeadersMap.get("studentRollNumber".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentRollNumber".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
								classRollnumberSectionInformation += "|"
										+ ((int) currentCellMarker.getNumericCellValue());
						}
						if (null != row.getCell(columnHeadersMap.get("studentSectionNumber".trim()))) {
							currentCellMarker = row.getCell(columnHeadersMap.get("studentSectionNumber".trim()));
							if (currentCellMarker.getCellType() == Cell.CELL_TYPE_NUMERIC)
								classRollnumberSectionInformation += "|"
										+ ((int) currentCellMarker.getNumericCellValue());
						}
						studentDTO.setClassRollnumberSectionInformation(classRollnumberSectionInformation);

						credentialDTO
								.setPassword(passwordEncoder.encode(studentFistName + studentLastName ));
						String usernameCandidate = studentFistName + studentLastName.substring(0, 1);
						int iterator = 1;
						while (null != credentialsRepository.findByUserName(usernameCandidate)) {
							usernameCandidate = studentFistName + studentLastName.substring(0, 1) + iterator;
							iterator++;
						}
						credentialDTO.setUserName(usernameCandidate);
						credentialDTO.setIsAdmin((byte) 0);
						credentialDTO.setAccountCreationDate(currentDate);
						credentialDTO.setPasswordLastChangedDate(currentDate);
						credentialDTO.setNumberOfRetry(0);
						credentialDTO.setIsActive((byte) 1);

						Credential credential = new Credential();
						BeanUtils.copyProperties(credentialDTO, credential);
						credential.setLinkedStudentId(Integer.toString(studentDTO.getStudentId()));
						Student student = new Student();
						BeanUtils.copyProperties(studentDTO, student);

						student.setSchoolspecific(schoolSpecific);
						student.setAddress(address);
						student.setCredential(credential);

						address.setStudent(student);
						student.setLinkedAddressId(address.getAddressId());

						studentRepository.save(student);

						studentDTO = null;
						addressDTO = null;
						credentialDTO = null;

					}
				}

			}
		} else {

		}
		uploadedWorkbook.close();
	}

}
