import { Route, Routes, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import EnrollmentPage from "./pages/EnrollmentPage";
import { useAuthContext } from "./contexts/AuthContext";
import StudentPage from "./pages/StudentPage";
import CalendarPage from "./pages/CalendarPage";

function App() {
  const { token } = useAuthContext();

  return (
    <Routes>
      <Route
        path="/"
        element={
          token ? <Navigate to="/student-home" /> : <Navigate to="/login" />
        }
      />
      <Route path="/login" element={token ? <StudentPage /> : <LoginPage />} />
      <Route
        path="/student-home"
        element={token ? <StudentPage /> : <LoginPage />}
      />
      <Route
        path="/enrollment"
        element={token ? <EnrollmentPage /> : <LoginPage />}
      />

      <Route
        path="/calendar"
        element={token ? <CalendarPage /> : <LoginPage />}
      />
    </Routes>
  );
}

export default App;
