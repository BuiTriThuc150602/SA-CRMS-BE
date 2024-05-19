import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import { toast } from "react-hot-toast";

const useCourseHook = () => {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(false);

    const getCourses = async (semester, faculty) => {
        setLoading(true);
        try {
            const response = await axiosInstance.get("/course/search", {
                params: { semester, faculty }
            });
            const data = response.data;
            if (response.status === 200) {
                setCourses(data);
            } else {
                toast.error(data.message);
            }
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Get courses failed! Please try again.");
            }
        }
        setLoading(false);
    };

    const getCoursesById = async (courseId) => {
        setLoading(true);
        try {
            const response = await axiosInstance.get("/course/by-courseId", {
                params: { courseId }
            });
            const data = response.data;
            if (response.status === 200) {
                setCourses(data);
                setError(null);
            } else {
                setError(data.message);
                toast.error(data.message);
            }
        } catch (error) {
            if (error.response) {
                setError(error.response.data.message);
                toast.error(error.response.data.message);
            } else {
                setError("Get course by ID failed! Please try again.");
                toast.error("Get course by ID failed! Please try again.");
            }
        }
        setLoading(false);
    };

    return { courses, loading, getCourses, getCoursesById};
};

export default useCourseHook;
