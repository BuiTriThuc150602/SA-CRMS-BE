import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuthContext } from "../contexts/AuthContext";
import useScheduleHook from "../hooks/useScheduleHook";
import useClassServiceHook from "../hooks/useClassServiceHook";
import useCourseHook from "../hooks/useCourseHook";
import { IoIosCheckboxOutline } from "react-icons/io";
import { GrSchedule } from "react-icons/gr";
import { RiGraduationCapLine } from "react-icons/ri";
import { IoHomeOutline } from "react-icons/io5";

export default function Calendar() {
  const [dataSource, setDataSource] = useState(null);
  const [semester, setSemester] = useState("HK1(2020-2021)");

  const daysOfWeek = [
    "Thứ 2",
    "Thứ 3",
    "Thứ 4",
    "Thứ 5",
    "Thứ 6",
    "Thứ 7",
    "Chủ nhật",
  ];

  const timeSlots = ["Buổi sáng", "Buổi chiều", "Buổi tối"];

  const location = useLocation();
  const student = location.state?.student;
  const { setToken } = useAuthContext();
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
    navigate("/login");
  };

  const {
    schedules,
    getSchedulesByEnrollment,
    loadingSchedule,
    clearSchedules,
  } = useScheduleHook();

  useEffect(() => {
    if (student) {
      getSchedulesByEnrollment(student.id, semester);
    }
  }, [student, semester]);

  const getTimeSlot = (lesson) => {
    if (["T1-3", "T4-6"].includes(lesson)) return "Buổi sáng";
    if (["T7-9", "T10-12"].includes(lesson)) return "Buổi chiều";
    if (["T13-15"].includes(lesson)) return "Buổi tối";
    return null;
  };

  const getDayIndex = (dayOfWeek) => {
    switch (dayOfWeek) {
      case "MONDAY":
        return 0;
      case "TUESDAY":
        return 1;
      case "WEDNESDAY":
        return 2;
      case "THURSDAY":
        return 3;
      case "FRIDAY":
        return 4;
      case "SATURDAY":
        return 5;
      case "SUNDAY":
        return 6;
      default:
        return -1;
    }
  };

  const renderScheduleDetails = (slot, day) => {
    return schedules
      .filter(
        (schedule) =>
          getTimeSlot(schedule.lesson) === slot &&
          getDayIndex(schedule.dayOfWeek) === daysOfWeek.indexOf(day)
      )
      .map((schedule) => (
        <div key={schedule.id} className="schedule-item p-2 border border-black rounded mb-1" >
          <div className="text-lg font-semibold">{schedule.courseName}</div>
          <div>{schedule.className}-</div>
          <div>{schedule.enrollmentClassId}</div>
          <div>Tiết: {schedule.lesson}</div>
          <div>Phòng: {schedule.room}</div>
          <div>GV: {schedule.teacherId}</div>
        </div>
      ));
  };
  

  return (
    <div className="w-screen h-screen font-roboto">
      <div className="flex relative justify-center items-center h-[15%] w-screen shadow-xl bg-white">
        <div className="flex fix">
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
              CỔNG THÔNG TIN SINH VIÊN
            </h1>
          </div>
        </div>
        <div className="absolute flex justify-between items-center right-[100px]">
          <img
            src={student.avatar}
            alt="avatar"
            className="w-16 h-16 rounded-full shadow-lg object-cover mr-5"
          />
          <details className="dropdown">
            <summary className="m-1 btn btn-outline">{student.name}</summary>
            <ul className="p-2 shadow menu dropdown-content z-[1] bg-base-100 rounded-box w-52">
              <li>
                <button>Đổi mật khẩu</button>
              </li>
              <li>
                <button onClick={handleLogout}>Đăng xuất</button>
              </li>
            </ul>
          </details>
        </div>
      </div>
      <div className="">
        <div className="mt-4 h-full flex flex-col justify-center items-center ml-44">
          <h2 className="text-xl font-bold mb-2">Lịch trong tuần</h2>
          <table className="border-collapse border border-gray-400">
            <thead>
              <tr>
                <th className="border border-gray-400"></th>
                {daysOfWeek.map((day, index) => (
                  <th
                    key={index}
                    className="border border-gray-400 p-10 text-center"
                  >
                    {day}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {timeSlots.map((slot, index) => (
                <tr key={index}>
                  <td className="border border-gray-400 p-2 font-bold bg-yellow-200">
                    {slot}
                  </td>
                  {daysOfWeek.map((day, idx) => (
                    <td
                      key={idx}
                      className="border border-gray-400 p-10 bg-blue-100"
                    >
                      {loadingSchedule ? (
                        <span className="loading loading-spinner"></span>
                      ) : (
                        renderScheduleDetails(slot, day)
                      )}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {/* menu */}
      <div className="flex flex-col w-[14%] border rounded-xl shadow-2xl absolute top-[18%] left-5 p-2 bg-white">
        <div className="flex flex-col">
          <button
            className="flex px-2 py-3 w-full border-b rounded-lg hover:bg-gray-200"
            onClick={() => {
              navigate("/student-home");
            }}
          >
            <IoHomeOutline size={20} color="grey" className="mr-3" />
            <p className="text-gray-700 font-roboto">Thông Tin Cá Nhân</p>
          </button>
          <button
            className="flex px-2 py-3 w-full border-b rounded-lg hover:bg-gray-200"
            onClick={() => {
              // navigate to enrollment page and pass the student as props
              navigate("/calendar", { state: { student: student } });
            }}
          >
            <GrSchedule size={20} color="grey" className="mr-3" />
            <p className="text-gray-700 font-roboto">Thời Khóa Biểu</p>
          </button>
          <button className="flex px-2 py-3 w-full border-b rounded-lg hover:bg-gray-200">
            <RiGraduationCapLine size={20} color="grey" className="mr-3" />
            <p className="text-gray-700 font-roboto">Kết Quả Học Tập</p>
          </button>
          <button
            className="flex px-2 py-3 w-full rounded-lg hover:bg-gray-200"
            onClick={() => {
              // navigate to enrollment page and pass the student as props
              navigate("/enrollment", { state: { student: student } });
            }}
          >
            <IoIosCheckboxOutline size={20} color="grey" className="mr-3" />
            <p className="text-gray-700 font-roboto">Đăng Ký Học Phần</p>
          </button>
        </div>
      </div>
    </div>
  );
}
