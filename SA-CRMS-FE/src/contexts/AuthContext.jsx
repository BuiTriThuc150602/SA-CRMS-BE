import { createContext, useState, useEffect, useContext   } from "react";


const AuthContext = createContext();

export const useAuthContext = () => {
    return useContext(AuthContext);
    }

export const AuthContextProvider = ({ children }) => {
    const [authenticate, setAuthenticate] = useState(
        JSON.parse(localStorage.getItem("authenticate"))
    );
    const [token, setToken] = useState(
        JSON.parse(localStorage.getItem("token"))
    );

    useEffect(() => {
        if (authenticate) {
            localStorage.setItem("authenticate", JSON.stringify(authenticate));
        } else {
            localStorage.removeItem("authenticate");
        }
        if (token) {
            localStorage.setItem("token", JSON.stringify(token));
        } else {
            localStorage.removeItem("token");
        }
    }, [authenticate, token]);

    return (
        <AuthContext.Provider
            value={{
                authenticate,
                setAuthenticate,
                token,
                setToken
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}