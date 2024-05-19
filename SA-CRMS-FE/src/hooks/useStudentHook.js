import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import { toast } from "react-hot-toast";

const useStudentHook = () => {
    const [student, setStudent] = useState({});
    const [loading, setLoading] = useState(false);
    const getStudent = async () => {
        setLoading(true);
        try {
            const response = await axiosInstance.get("/students/info");
            const data = response.data;
            console.log(response);
            if (response.status === 200) {
                setStudent(data.result);
            } else {
                toast.error(data.message);
            }
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Get student failed! Please try again.");
            }
        }
        setLoading(false);
    };
    return { student, loading, getStudent };
};
export default useStudentHook;