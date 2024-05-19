import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import { toast } from "react-hot-toast";

const useClassServiceHook = () => {
    const [enrollmentClasses, setEnrollmentClasses] = useState([]);
    const [loadingClass, setLoading] = useState(false);

    const getEnrollmentClassesByCourse = async (courseId) => {
        setLoading(true);
        try {
            const response = await axiosInstance.get("/class/enrollmentclass/by-course", {
                params: { courseId }
            });
            const data = response.data;
            if (response.status === 200) {
                setEnrollmentClasses(data);
            } else {
                // toast.error(data.message);
            }
        } catch (error) {
            if (error.response) {
                // toast.error(error.response.data.message);
            } else {
                toast.error("Get enrollment classes failed! Please try again.");
            }
        }
        setLoading(false);
    };

    const updateCurrentStudent = async (enrollmentClassId) => {
        try {
            const response = await axiosInstance.post("/class/enrollmentclass/update-current-student", null, {
                params: { enrollmentClassId }
            });
            if (response.status === 200) {
                toast.success("Update successful!");
                // Update the local state if necessary
            } else {
                // toast.error(response.data.message);
            }
        } catch (error) {
            if (error.response) {
                // toast.error(error.response.data.message);
            } else {
                toast.error("Update current student failed! Please try again.");
            }
        }
    };


    const getEnrollClassById = async (enrollmentClassId) => {
        setLoading(true);
        try {
            const response = await axiosInstance.get("/class/enrollmentclass/by-id", {
                params: { enrollmentClassId }
            });
            const data = response.data;
            if (response.status === 200) {
                setEnrollmentClasses(data);
            } else {
                // toast.error(data.message);
            }
        } catch (error) {
            if (error.response) {
                // toast.error(error.response.data.message);
            } else {
                toast.error("Get enrollment class failed! Please try again.");
            }
        }
        setLoading(false);
    };

    const clearEnrollmentClasses = () => {
        setEnrollmentClasses([]);
    };

    return { enrollmentClasses, loadingClass, getEnrollmentClassesByCourse,clearEnrollmentClasses, updateCurrentStudent, getEnrollClassById};
};

export default useClassServiceHook;
