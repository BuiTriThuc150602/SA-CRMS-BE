import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import { toast } from "react-hot-toast";

const useScheduleHook = () => {
  const [schedules, setSchedules] = useState([]);
  const [checkDuplicated, setCheckDuplicated] = useState([]);
  const [loadingSchedule, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const getSchedulesByEnrollment = async (studentId, semester) => {
    setLoading(true);
    try {
      const response = await axiosInstance.get("/schedule/by-enrollment", {
        params: { studentId, semester },
      });
      const data = response.data;

      if (response.status === 200) {
        setSchedules(data);
        setError(null);
      } else {
        setError(data.error); // Set error message
      }
    } catch (error) {
      if (error.response) {
        setError(error.response.data.error);
      } else {
        toast.error("Get schedules failed! Please try again.");
      }
    }
    setLoading(false);
  };

  const getSchedulesByEnrollmentClass = async (enrollmentClassId) => {
    setLoading(true);
    try {
      const response = await axiosInstance.get(
        "/schedule/by-enrollment-class",
        {
          params: { enrollmentClassId },
        }
      );
      const data = response.data.data;

      if (response.status === 200) {
        setSchedules(data);
        setError(null);
      } else {
        setError(data.error); // Set error message
      }
    } catch (error) {
      if (error.response) {
        setError(error.response.data.error);
      } else {
        toast.error("Get schedules failed! Please try again.");
      }
    }
    setLoading(false);
  };

  const checkDuplicatedSchedule = async (studentId, semester, scheduleId) => {
    try {
      const response = await axiosInstance.get("/schedule/check-duplicated-schedule", {
        params: { studentId, semester, scheduleId },
      });
      const data = response.data;
      if (response.status === 200) {
        setCheckDuplicated(data);
        setError(null);
        return data;
      } else {
        setError(data.error); // Set error message
        return false;
      }
    } catch (error) {
      if (error.response) {
        toast.error(error.response.data.message);
      } else {
        toast.error("Check duplicated schedule failed! Please try again.");
      }
      return false;
    }
  };

  const clearSchedules = () => {
    setSchedules([]);
  };

  return {
    schedules,
    loadingSchedule,
    getSchedulesByEnrollmentClass,
    clearSchedules,
    error,
    setError,
    checkDuplicatedSchedule,
    checkDuplicated,
    getSchedulesByEnrollment
  };
};

export default useScheduleHook;
