import { useEffect, useState } from "react";

// routerdom
import { useNavigate } from "react-router-dom";

// custome Hooks
import useStudentHook from "../hooks/useStudentHook";

// Icon
import { IoHomeOutline } from "react-icons/io5";
import { GrSchedule } from "react-icons/gr";
import { RiGraduationCapLine } from "react-icons/ri";
import { IoIosCheckboxOutline } from "react-icons/io";
import { useAuthContext } from "../contexts/AuthContext";

const StudentPage = () => {
  const { student, loading, getStudent } = useStudentHook();
  const [studentInfo, setStudentInfo] = useState({});
  const navigate = useNavigate();
  const { setToken } = useAuthContext();

  const studentStatus = {
    ENROLLED: "Đang Học",
    GRADUATED: "Đã Tốt Nghiệp",
    DROPOUT: "Bỏ Học",
    SUSPENDED: "Đình Chỉ",
  };

  useEffect(() => {
    getStudent();
  }, []);

  useEffect(() => {
    setStudentInfo(student);
  }, [student]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
    navigate("/login");
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
        <div className="absolute flex justify-between items-center right-8">
          <img
            src={studentInfo.avatar}
            alt="avatar"
            className="w-16 h-16 rounded-full shadow-lg object-cover mr-5"
          />
          <details className="dropdown">
            <summary className="m-1 btn btn-outline">
              {studentInfo.name}
            </summary>
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
      <div className="flex justify-center items-center w-screen h-[90%] overflow-auto bg-sky-300">
        <div className="flex flex-col w-[70%] h-[98%] p-5 rounded-2xl shadow-lg border overflow-auto bg-white">
          <div className="flex">
            <div className="mt-5 mb-2 border rounded-xl h-96 w-[28%] mr-3 overflow-hidden">
              {/* avatar */}
              <div className="flex justify-center items-center w-full mt-5">
                {loading ? (
                  <span className="loading loading-spinner"></span>
                ) : (
                  <img
                    src={studentInfo.avatar}
                    alt="avatar"
                    className="w-36 h-36 rounded-full"
                  />
                )}
              </div>
              {/* info */}
              <div className="m-5 flex flex-col w-full overflow-hidden">
                <div className="flex mt-3 ml-5">
                  <p className="mr-3  text-md font-semibold">Mã Sinh Viên:</p>
                  <div className="text-gray-700">{studentInfo.id}</div>
                </div>
                <div className="flex mt-3 ml-5">
                  <p className="mr-3  text-md font-semibold">Họ và Tên:</p>
                  <div className="text-gray-700">{studentInfo.name}</div>
                </div>
                <div className="flex mt-3 ml-5">
                  <p className="mr-3  text-md font-semibold">Giới tính</p>
                  <div className="text-gray-700">
                    {studentInfo.gender ? "Nữ" : "Nam"}
                  </div>
                </div>
              </div>
            </div>
            <div className="mt-5 mb-2 border rounded-xl h-96 w-[70%] overflow-hidden">
              {/* eduaction info */}
              <div className="flex flex-col w-[3/5] h-[90%] p-5 overflow-hidden">
                <div className="mt-5 mb-2">
                  <h1 className="text-2xl font-bold mb-3 border-b text-gray-700 ">
                    Thông Tin Học Vấn
                  </h1>
                </div>
                <div className="grid grid-cols-2 gap-5">
                  <div className="flex flex-col">
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">
                        Trình Độ Học Vấn:
                      </p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.educationalLevel}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">Khoa:</p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.faculty}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">Ngành:</p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.major}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">Khóa:</p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.studentCourse}
                      </div>
                    </div>
                  </div>
                  <div className="flex flex-col">
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">Lớp:</p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.studentClass}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">
                        Hình Thức Học:
                      </p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.studentType}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">
                        Chuyên Ngành:
                      </p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.studentSpecialization}
                      </div>
                    </div>
                    <div className="flex mt-3">
                      <p className="mr-3  text-xl font-semibold">
                        Năm Tốt Nghiệp:
                      </p>
                      <div className="text-xl text-gray-700 font-semibold">
                        {studentInfo.studentGraduationYear}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          {/* personal info */}
          <div className="mt-5 mb-2 border rounded-xl h-96 w-full overflow-auto">
            <div className="flex flex-col w-[3/5] h-[90%] p-5">
              <div className="mt-5 mb-2">
                <h1 className="text-2xl font-bold mb-3 border-b text-gray-700">
                  Thông Tin Cá Nhân
                </h1>
              </div>
              <div className="grid grid-cols-2 gap-5">
                <div className="flex flex-col">
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Email:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.email}
                    </div>
                  </div>
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Nơi Sinh:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.placeOfBirth}
                    </div>
                  </div>
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Ngày Sinh:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.dateOfBirth}
                    </div>
                  </div>
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">
                      Số Điện Thoại:
                    </p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.phone}
                    </div>
                  </div>
                </div>
                <div className="flex flex-col">
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Địa Chỉ:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.address}
                    </div>
                  </div>
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Mã Hồ Sơ:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentInfo.studentCode}
                    </div>
                  </div>
                  <div className="flex mt-3">
                    <p className="mr-3  text-xl font-semibold">Trạng Thái:</p>
                    <div className="text-xl text-gray-700 font-semibold">
                      {studentStatus[studentInfo.studentStatus]}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {/* menu */}
      <div className="flex flex-col w-[14%] border rounded-xl shadow-2xl absolute top-[16%] left-4 p-2 bg-white">
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
              navigate("/calendar", { state: { student: studentInfo } });
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
              navigate("/enrollment", { state: { student: studentInfo } });
            }}
          >
            <IoIosCheckboxOutline size={20} color="grey" className="mr-3" />
            <p className="text-gray-700 font-roboto">Đăng Ký Học Phần</p>
          </button>
        </div>
      </div>
    </div>
  );
};
export default StudentPage;
