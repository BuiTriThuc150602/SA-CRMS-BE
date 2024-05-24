import { useAuthContext } from "../contexts/AuthContext";
import { useState } from "react";
import axiosInstance from "../configs/api/axiosInstance";
import toast from "react-hot-toast";

const useLogin = () => {
    const [loading, setLoading] = useState(false);
    const { setToken } = useAuthContext();

    const login = async (id, password) => {
        setLoading(true);
        try {
            const response = await axiosInstance.post("/auth/login", {
                id,
                password,
            });

            const data = response.data;
            console.log(response);
            console.log(data.result.message );
            if (data.result.message === undefined && response.status === 200 ) {
                setToken(data.result.token);
            } else {
                toast.error(data.result.message);
            }
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Login failed! Please try again.");
            }
        }
        setLoading(false);

    };
    return { login, loading };
};

export default useLogin;