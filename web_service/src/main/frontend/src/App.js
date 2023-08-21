import Login from "./pages/Login.js";
import Signup from "./pages/Signup.js";
import Dashboard from "./pages/Dashboard.js";
import Employee from "./pages/Employee.js";
import EmployeeCreate from "./pages/EmployeeCreate.js";
import EmployeeUpdate from "./pages/EmployeeUpdate.js";
import EmployeeDelete from "./pages/EmployeeDelete.js";
import Notification from "./pages/Notification.js";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import AuthGuard from "./pages/AuthGuard.js";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/signup" element={<Signup/>}/>
                <Route element={<AuthGuard />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/employee" element={<Employee />} />
                    <Route path="/employee/create" element={<EmployeeCreate />} />
                    <Route path="/employee/update" element={<EmployeeUpdate />} />
                    <Route path="/employee/delete" element={<EmployeeDelete />} />
                    <Route path="/notification" element={<Notification />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;