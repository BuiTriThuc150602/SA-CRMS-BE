import { useEffect, useState } from "react";
import useCourseHook from "../hooks/useCourseHook";
import useClassServiceHook from "../hooks/useClassServiceHook";
import useScheduleHook from "../hooks/useScheduleHook";
import useEnrollmentServiceHook from "../hooks/useEnrollmentServiceHook";

import { IoCheckmarkCircle, IoCloseCircle } from "react-icons/io5";
import { IoMdArrowDropdown } from "react-icons/io";
import { useLocation, useNavigate } from "react-router-dom";
import { format } from "date-fns";
import { BsThreeDots } from "react-icons/bs";
import toast from "react-hot-toast";
import { useAuthContext } from "../contexts/AuthContext";

const EnrollmentPage = () => {
  const [semester, setSemsester] = useState("HK1(2020-2021)");
  const { courses, loading, getCourses } = useCourseHook();
  const {
    enrollmentClasses,
    getEnrollmentClassesByCourse,
    loadingClass,
    updateCurrentStudent,
    clearEnrollmentClasses,
  } = useClassServiceHook();
  const { setToken } = useAuthContext();

  const {
    schedules,
    getSchedulesByEnrollmentClass,
    loadingSchedule,
    clearSchedules,
    error,
    setError,
    checkDuplicatedSchedule,
    checkDuplicated,
  } = useScheduleHook();
  const {
    enrollments,
    loadingEnrollment,
    getEnrollmentsByStudentIdAndSemester,
    clearEnrollments,
    addEnrollment,
    setEnrollments,
  } = useEnrollmentServiceHook();
  const navigate = useNavigate();

  const [selectedCourseId, setSelectedCourseId] = useState([]);
  const [selectedEnrollmentClassId, setSelectedEnrollmentClassId] = useState(
    []
  );
  const [selectedScheduleId, setSelectedScheduleId] = useState([]);

  // get student info from location state passed from the student :>>>
  const location = useLocation();
  const student = location.state?.student;
  // console.log(student);
  // console.log(student.faculty);

  useEffect(() => {
    getCourses(semester, student.faculty);
  }, [semester, student.faculty]);

  useEffect(() => {
    if (selectedCourseId != null && selectedCourseId != "") {
      getEnrollmentClassesByCourse(selectedCourseId);
    } else {
      clearEnrollmentClasses();
    }
  }, [selectedCourseId]);
  // console.log(enrollmentClasses);

  useEffect(() => {
    if (selectedEnrollmentClassId != null && selectedEnrollmentClassId != "") {
      getSchedulesByEnrollmentClass(selectedEnrollmentClassId);
    } else {
      clearSchedules();
    }
  }, [selectedEnrollmentClassId]);
  // console.log(schedules);

  useEffect(() => {
    if (selectedCourseId != null) {
      getEnrollmentsByStudentIdAndSemester(student.id, semester);
    } else {
      clearEnrollments();
    }
  }, [student.id, semester]);
  console.log(enrollments);

  console.log("selectedCourseId " + selectedCourseId);
  const handleRowClickCourse = (id) => {
    setSelectedCourseId(id === selectedCourseId ? null : id);
  };

  console.log("selectedEnrollmentClassId " + selectedEnrollmentClassId);
  const handleRowClickEnrollmentClass = (id) => {
    setSelectedEnrollmentClassId(id === selectedEnrollmentClassId ? null : id);
  };

  console.log("selectedScheduleId " + selectedScheduleId);
  const handleRowClickSchedule = (id) => {
    setSelectedScheduleId(id === selectedScheduleId ? null : id);
  };

  useEffect(() => {
    // Khi selectedCourseId thay đổi, gọi handleRowClickEnrollmentClass('')
    handleRowClickEnrollmentClass("");
  }, [selectedCourseId]);

  useEffect(() => {
    // Khi selectedCourseId thay đổi, gọi handleRowClickEnrollmentClass('')
    handleRowClickSchedule("");
  }, [selectedCourseId, selectedEnrollmentClassId]);

  const dayOfWeekMap = {
    MONDAY: "Thứ 2",
    TUESDAY: "Thứ 3",
    WEDNESDAY: "Thứ 4",
    THURSDAY: "Thứ 5",
    FRIDAY: "Thứ 6",
    SATURDAY: "Thứ 7",
    SUNDAY: "Chủ Nhật",
  };

  // console.log(courses);

  console.log("error " + error);
  const handleDialogClose = () => {
    setError(null); // Clear error to close dialog
    setSelectedEnrollmentClassId(null);
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return format(date, "dd/MM/yyyy");
  };

  //Xử lí đăng kí học phần
  const handleRegisterClick = async () => {
    const isDuplicated = await checkDuplicatedSchedule(
      student.id,
      semester,
      selectedScheduleId
    );
    console.log();
    if (isDuplicated) {
      toast.error("Lịch học trùng");
    } else {
      const enrollmentRequest = {
        id: "HP" + selectedEnrollmentClassId,
        studentId: student.id,
        courseId: selectedCourseId,
        classId: selectedEnrollmentClassId,
        scheduleId: selectedScheduleId,
        semester: semester,
        deadline: new Date().toISOString(),
        enrollmentStatus: 0,
        collectionStatus: 1,
        registrationDate: new Date().toISOString(),
        cancellationDate: null,
        tuitionFee: 1200000,
      };

      await addEnrollment(enrollmentRequest);
      updateCurrentStudent(enrollmentRequest.classId);
      window.location.reload(); // Reload lại trang
    }
  };

  // useEffect(()=>{
  //   if (error !== "Bad Request" && error !== null) {
  //     setSelectedScheduleId(null);
  //   }
  // },[error]);
  

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
    navigate("/login");
  };

  return (
    <div className="w-screen h-screen font-roboto overflow-y-auto">
      <div className="flex justify-center items-center h-[12%] w-full border shadow-lg shadow-[#e4eff5] bg-white ">
        <div className="flex">
          <img
            src="https://inkythuatso.com/uploads/thumbnails/800/2021/11/logo-iuh-inkythuatso-01-08-11-18-25.jpg"
            alt="iuh logo"
            className="w-[150px]"
          />
          <div className="flex flex-col justify-center items-center">
            <h1 className="text-xl font-extrabold text-[#08387f]">
              TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP TP.HCM
            </h1>
            <h1 className="text-xl font-extrabold text-[#920000]">
              CỔNG ĐĂNG KÍ HỌC PHẦN SINH VIÊN
            </h1>
          </div>
        </div>
      </div>
      <div className="flex items-center border bg-white w-full mx-auto px-5 max-w-full overflow-hidden">
        <div className="flex w-60 flex-col bg-sky-500 text-white p-2 pl-7 pr-7">
          <p>Xin chào!</p>
          <p className="text-lg font-semibold">{student.name}</p>
          <div className="flex justify-between">
            <p>Giới tính:</p>
            <p>{student.gender ? "Nữ" : "Nam"}</p>
          </div>
          <div className="flex justify-between">
            <p>MSSV:</p>
            <p>{student.id}</p>
          </div>
          <div className="flex justify-between">
            <p>Trạng thái:</p>
            <p>
              {student.studentStatus === "ENROLLED" ? "Đang học" : "Tốt nghiệp"}
            </p>
          </div>

          <button
            className="bg-amber-500 mt-2 pr-3 pl-3 p-1"
            onClick={handleLogout}
          >
            Đăng xuất
          </button>
        </div>
        <div className="flex">
          <div
            style={{ width: "200px", height: "auto" }}
            className="flex items-center justify-center"
          >
            <img src={student.avatar} alt="avatar" />
          </div>

          <div className="flex flex-col justify-center ml-2 text-sky-500">
            <a
              onClick={() => {
                navigate("/student-home");
              }}
            >
              THÔNG TIN SINH VIÊN
            </a>
            <a href="">CHƯƠNG TRÌNH KHUNG</a>
          </div>
        </div>
      </div>

      <div className="flex flex-col items-center justify-center border w-full mx-auto px-5 max-w-full overflow-hidden">
        <p className="text-2xl font-bold text-sky-700 mt-2">ĐĂNG KÍ HỌC PHẦN</p>
        <div className="flex items-center mt-5">
          <p className="text-base font-semibold ">Đợt đăng kí: </p>
          <div className="dropdown">
            <div
              tabIndex={0}
              role="button"
              className="flex items-center ml-2 pl-2 pr-2 border border-gray-500 rounded"
            >
              {semester}
              <IoMdArrowDropdown className="ml-2" />
            </div>
            <ul
              tabIndex={0}
              className="dropdown-content z-[1] menu p-2 shadow border border-gray-400 bg-base-100 rounded-box w-52"
            >
              <li>
                <a
                  onClick={() => setSemsester("HK2(2023-2024)")}
                  className={semester === "HK2(2023-2024)" ? "bg-sky-400" : ""}
                >
                  HK2 (2023-2024)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK1(2023-2024)")}
                  className={semester === "HK1(2023-2024)" ? "bg-sky-400" : ""}
                >
                  HK1 (2023-2024)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK2(2022-2023)")}
                  className={semester === "HK2(2022-2023)" ? "bg-sky-400" : ""}
                >
                  HK2 (2022-2023)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK1(2022-2023)")}
                  className={semester === "HK1(2022-2023" ? "bg-sky-400" : ""}
                >
                  HK1 (2022-2023)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK2(2021-2022)")}
                  className={semester === "HK2(2021-2022)" ? "bg-sky-400" : ""}
                >
                  HK2 (2021-2022)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK1(2021-2022)")}
                  className={semester === "HK1(2021-2022)" ? "bg-sky-400" : ""}
                >
                  HK1 (2021-2022)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK2(2020-2021)")}
                  className={semester === "HK2(2020-2021)" ? "bg-sky-400" : ""}
                >
                  HK2 (2020-2021)
                </a>
              </li>
              <li>
                <a
                  onClick={() => setSemsester("HK1(2020-2021)")}
                  className={semester === "HK1(2020-2021)" ? "bg-sky-400" : ""}
                >
                  HK1 (2020-2021)
                </a>
              </li>
            </ul>
          </div>
          <input
            type="radio"
            name="radio-4"
            className="radio radio-accent ml-3"
            checked
          />
          <p className="text-red-700 font-bold">HỌC MỚI</p>
          <input
            type="radio"
            name="radio-4"
            className="radio radio-accent ml-3"
          />
          <p className="text-red-700 font-bold">HỌC LẠI</p>
          <input
            type="radio"
            name="radio-4"
            className="radio radio-accent ml-3"
          />
          <p className="text-red-700 font-bold">HỌC CẢI THIỆN</p>
        </div>
        <p className="text-lg font-bold text-orange-500 mt-5">
          MÔN HỌC PHẦN CHỜ ĐĂNG KÍ
        </p>
        <div className="overflow-x-auto mt-3">
          <table className="table border-collapse border border-blue-300">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="border border-blue-300"></th>
                <th className="border border-blue-300">STT</th>
                <th className="border border-blue-300">Mã HP</th>
                <th className="border border-blue-300">Tên môn học</th>
                <th className="border border-blue-300">TC</th>
                <th className="border border-blue-300">Bắt buộc</th>
                <th className="border border-blue-300">
                  Học phần: học trước (a), tiên quyết (b), song hành (c)
                </th>
              </tr>
            </thead>
            <tbody>
              {loading ? (
                <tr>
                  <td colSpan="4">Loading...</td>
                </tr>
              ) : (
                courses.map((course, index) => (
                  <tr
                    key={index}
                    onClick={() => handleRowClickCourse(course.id)}
                    className={
                      selectedCourseId === course.id ? "bg-amber-200" : ""
                    }
                  >
                    <th className="border border-blue-300">
                      <label>
                        <input
                          type="radio"
                          className="radio border border-gray-400"
                          checked={course.id === selectedCourseId}
                          onChange={(e) => e.stopPropagation()}
                        />
                      </label>
                    </th>
                    <td className="border border-blue-300">{index + 1}</td>
                    <td className="border border-blue-300">{course.id}</td>
                    <td className="border border-blue-300">{course.name}</td>
                    <th className="border border-blue-300">{course.credit}</th>
                    <td className="border border-blue-300">
                      <div className="flex items-center justify-center">
                        <IoCloseCircle color="red" size={20} />
                      </div>
                    </td>
                    <td className="border border-blue-300">
                      <div className="flex items-center justify-center">
                        {course.prerequisiteCourseIds.length > 0 &&
                          course.prerequisiteCourseIds.map((id, index1) => {
                            console.log("id " + id);
                            return (
                              <div className="flex" key={index1}>
                                {id}
                                <p className="text-red-600 font-bold">(a)</p>
                              </div>
                            );
                          })}
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        <div className="flex items-center justify-center mt-5">
          <p className="text-lg font-bold text-orange-500 ">
            LỚP HỌC PHẦN CHỜ ĐĂNG KÍ
          </p>

          <label className="flex ml-3">
            <input
              type="checkbox"
              className="checkbox border border-gray-400 mr-2"
            />
            <p className="text-red-600 font-bold">
              HIỂN THỊ LỚP HỌC PHẦN KHÔNG TRÙNG LỊCH
            </p>
          </label>
        </div>

        <div className="overflow-x-auto mt-3">
          <table className="table border-collapse border border-blue-300">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="border border-blue-300"></th>
                <th className="border border-blue-300">STT</th>
                <th className="border border-blue-300">Mã LHP</th>
                <th className="border border-blue-300">Tên lớp học phần</th>
                <th className="border border-blue-300">Lớp dự kiến</th>
                <th className="border border-blue-300">Sỉ số tối đa</th>
                <th className="border border-blue-300">Đã đăng kí</th>
                <th className="border border-blue-300">Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              {loadingClass ? (
                <tr>
                  <td colSpan="4">Loading...</td>
                </tr>
              ) : (
                selectedCourseId !== null &&
                selectedCourseId !== "" &&
                enrollmentClasses.map((enrollmentClass, index) => (
                  <tr
                    key={index}
                    onClick={() =>
                      handleRowClickEnrollmentClass(enrollmentClass.id)
                    }
                    className={
                      selectedEnrollmentClassId === enrollmentClass.id
                        ? "bg-amber-200"
                        : ""
                    }
                  >
                    <th className="border border-blue-300">
                      <label>
                        <input
                          type="radio"
                          className="radio border border-gray-400"
                          checked={
                            enrollmentClass.id === selectedEnrollmentClassId
                          }
                          onChange={(e) => e.stopPropagation()}
                        />
                      </label>
                    </th>
                    <td className="border border-blue-300">{index + 1}</td>
                    <td className="border border-blue-300">
                      {enrollmentClass.id}
                    </td>
                    <td className="border border-blue-300">
                      {enrollmentClass.courseName}
                    </td>
                    <th className="border border-blue-300">
                      {enrollmentClass.className}
                    </th>
                    <th className="border border-blue-300">
                      {enrollmentClass.maxStudent}
                    </th>
                    <th className="border border-blue-300">
                      {enrollmentClass.currentStudents}
                    </th>
                    <th className="border border-blue-300">
                      {enrollmentClass.statusEnrollClass === "LOCKED"
                        ? "Đã khóa"
                        : "Đang lên kế hoạch"}
                    </th>
                  </tr>
                ))
              )}

              {error !== "Bad Request" && error !== null && (
                <div className="fixed inset-0 flex items-center justify-center z-50">
                  <div className="flex flex-col items-center justify-center bg-white p-4 border border-gray-300 shadow-lg rounded">
                    <p className="text-red-500 font-bold">
                      Lớp đã đủ số lượng!
                    </p>
                    <button
                      onClick={handleDialogClose}
                      className="mt-4 bg-blue-500 text-white px-4 py-2 rounded"
                    >
                      Xác nhận
                    </button>
                  </div>
                </div>
              )}
            </tbody>
          </table>
        </div>

        <p className="text-lg font-bold text-orange-500 mt-5">
          CHI TIẾT LỚP HỌC PHẦN
        </p>

        <div className="flex items-center  mt-7">
          <p className="mr-2">Nhóm thực hành</p>
          <div className="flex border border-gray-400 rounded items-center  ">
            <input type="text" className="pl-2 focus:outline-none w-40" />
            <IoMdArrowDropdown />
          </div>
          <button className="bg-amber-500 ml-3 pr-3 pl-3 text-white">
            Xem lịch trùng
          </button>
        </div>

        <div className="overflow-x-auto mt-3">
          <table className="table border-collapse border border-blue-300">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="border border-blue-300">STT</th>
                <th className="border border-blue-300">Lịch học</th>
                <th className="border border-blue-300">Nhóm thực hành</th>
                <th className="border border-blue-300">Phòng</th>
                <th className="border border-blue-300">Dãy nhà</th>
                <th className="border border-blue-300">Cơ sở</th>
                <th className="border border-blue-300">Giảng viên</th>
                <th className="border border-blue-300">Thời gian</th>
                <th className="border border-blue-300"></th>
              </tr>
            </thead>
            <tbody>
              {loadingSchedule ? (
                <tr>
                  <td colSpan="4">Loading...</td>
                </tr>
              ) : (
                selectedEnrollmentClassId !== null &&
                selectedEnrollmentClassId !== "" &&
                schedules.map((schedule, index) => (
                  <tr
                    key={index}
                    onClick={() => handleRowClickSchedule(schedule.id)}
                    className={
                      selectedScheduleId === schedule.id ? "bg-amber-200" : ""
                    }
                  >
                    <td className="border border-blue-300">{index + 1}</td>
                    <td className="border border-blue-300">
                      <p>
                        {dayOfWeekMap[schedule.dayOfWeek]} ({schedule.lesson})
                      </p>
                    </td>
                    <td className="border border-blue-300">
                      {schedule.practiceGroup}
                    </td>
                    <th className="border border-blue-300">{schedule.room}</th>
                    <th className="border border-blue-300">
                      {schedule.building}
                    </th>
                    <th className="border border-blue-300">
                      {schedule.facility}
                    </th>
                    <th className="border border-blue-300">
                      {schedule.teacherId}
                    </th>
                    <th className="border border-blue-300">
                      <p>
                        {schedule.startDate} - {schedule.finishDate}
                      </p>
                    </th>
                    <th className="border border-blue-300">
                      <button className="pl-2 pr-2 p-1 boder bg-cyan-600 text-white">
                        Xem
                      </button>
                    </th>
                  </tr>
                ))
              )}
            </tbody>
          </table>
          <div className="flex items-center justify-center">
            <button
              className=" bg-amber-500 mt-7 pr-3 pl-3 p-1 text-white"
              onClick={handleRegisterClick}
            >
              Đăng kí môn học
            </button>
          </div>
        </div>

        <p className="text-lg font-bold text-orange-500 mt-5">
          LỚP HỌC PHẦN ĐÃ ĐĂNG KÍ TRONG HỌC KÌ NÀY
        </p>

        <div className="overflow-x-auto mt-3">
          <table className="table border-collapse border border-blue-300">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="border border-blue-300">Thao tác</th>
                <th className="border border-blue-300">STT</th>
                <th className="border border-blue-300">Mã LHP</th>
                <th className="border border-blue-300">Tên môn học</th>
                <th className="border border-blue-300">Lớp học dự kiến</th>
                <th className="border border-blue-300">Số TC</th>
                <th className="border border-blue-300">Nhóm TH</th>
                <th className="border border-blue-300">Học phí</th>
                <th className="border border-blue-300">Hạn nộp</th>
                <th className="border border-blue-300">Thu</th>
                <th className="border border-blue-300">Trạng thái ĐK</th>
                <th className="border border-blue-300">Ngày ĐK</th>
                <th className="border border-blue-300">Trạng thái LHP</th>
              </tr>
            </thead>
            <tbody>
              {loadingEnrollment ? (
                <tr>
                  <td colSpan="4">Loading...</td>
                </tr>
              ) : (
                enrollments.map((enrollment, index) => (
                  <tr key={index}>
                    <td className="border border-blue-300">
                      <p>
                        <button>
                          <BsThreeDots size={20} />
                        </button>
                      </p>
                    </td>
                    <td className="border border-blue-300">{index + 1}</td>
                    <td className="border border-blue-300">
                      {enrollment.classId}
                    </td>
                    <th className="border border-blue-300">
                      {enrollment.courseName}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.className}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.credit}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.groupPractice}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.tuitionFee}
                    </th>
                    <th className="border border-blue-300">
                      {formatDate(enrollment.deadline)}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.collectionStatus === "COLLECTED" ? (
                        <IoCheckmarkCircle color="green" size={20} />
                      ) : (
                        <IoCloseCircle color="red" size={20} />
                      )}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.enrollmentStatus === "NEW"
                        ? "Đăng kí mới"
                        : "Đăng kí lại"}
                    </th>
                    <th className="border border-blue-300">
                      {formatDate(enrollment.registrationDate)}
                    </th>
                    <th className="border border-blue-300">
                      {enrollment.statusEnrollClass === "PLANNING"
                        ? "Đang lên kế hoạch"
                        : "Đã khóa"}
                    </th>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="flex flex-col bg-blue-500 mt-2 pt-2 pb-2 text-white items-center justify-center w-[90%] mx-auto">
        <p>TRƯỜNG ĐẠI HỌC CÔNG NGHIỆP TP.Hồ Chí Minh</p>
        <p>
          Địa chỉ: Số 12 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, TP. Hồ Chí Minh
        </p>
        <p>Điện thoại: 0283 8940 390</p>
        <p>Fax: 0283 9940 954</p>
        <p>Email: dhcn@iuh.edu.vn</p>
      </div>

      <div className="flex flex-col bg-sky-400 pt-2 pb-2 text-white items-center justify-center w-[90%] mx-auto">
        Bản quyền 2018 - Trường Đại học Công nghiep TP. Hồ Chí Minh
      </div>
    </div>
  );
};

export default EnrollmentPage;
