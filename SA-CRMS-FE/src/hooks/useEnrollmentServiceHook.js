import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import { toast } from "react-hot-toast";

const useEnrollmentServiceHook = () => {
  const [enrollments, setEnrollments] = useState([]);
  const [loadingEnrollment, setLoading] = useState(false);

  const getEnrollmentsByStudentIdAndSemester = async (studentId, semester) => {
    setLoading(true);
    try {
      const response = await axiosInstance.get("enrollment/search", {
        params: { studentId, semester },
      });
      const data = response.data;
      if (response.status === 200) {
        setEnrollments(data);
      } else {
        toast.error(data.message);
      }
    } catch (error) {
      if (error.response) {
        toast.error(error.response.data.message);
      } else {
        toast.error("Get enrollments failed! Please try again.");
      }
    }
    setLoading(false);
  };

  const addEnrollment = async (enrollmentRequest) => {
    try {
      const response = await axiosInstance.post(
        "/enrollment",
        enrollmentRequest
      );
      if (response.status === 201) {
        toast.success("Enrollment added successfully!");
      } else {
        toast.error("Failed to add enrollment!");
      }
    } catch (error) {
      if (error.response) {
        toast.error(error.response.data.message);
      } else {
        toast.error("Add enrollment failed! Please try again.");
      }
    }
  };

  const clearEnrollments = () => {
    setEnrollments([]);
  };

  return {
    enrollments,
    loadingEnrollment,
    getEnrollmentsByStudentIdAndSemester,
    clearEnrollments,
    addEnrollment,
    setEnrollments
  };
};

export default useEnrollmentServiceHook;
