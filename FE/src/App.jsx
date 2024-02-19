import { Routes, Route, useNavigate } from "react-router-dom";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Pendidikan from "./pages/Pendidikan";
import Pelatihan from "./pages/Pelatihan";
import Pengalaman from "./pages/Pengalaman";
import { API } from "./config/axiosHelper";
import { useContext, useEffect, useState } from "react";
import { UserContext } from "./context/userContext";
import AdminPage from "./pages/AdminPage";

function App() {
  const [biodata, setBiodata] = useState({});
  const [state, dispatch] = useContext(UserContext);
  let navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);
  const [role, setRole] = useState("");

  // Redirect Auth here ...
  useEffect(() => {
    // Redirect Auth
    // if (localStorage.token) {
    //   setAuthToken(localStorage.token);
    // }

    if (state.isLogin === false && !isLoading) {
      navigate("/");
    }
    console.log("Role :", state.user.role);
  }, [state]);
  const getBiodata = async () => {
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };
      const response = await API.get("/users/current", config);
      console.log("response.status :", response);
      if (response.status === 404) {
        return dispatch({
          type: "AUTH_ERROR",
        });
      }
      setBiodata(response);
    } catch (error) {
      dispatch({
        type: "AUTH_ERROR",
      });
      setIsLoading(false);
      console.log(error.response.status);
    }
  };
  useEffect(() => {
    getBiodata();
  }, []);
  return (
    <div>
      <Routes>
        <Route excact path="/" element={<Login />} />
        <Route
          path="/home"
          element={state.user.role == "admin" ? <AdminPage /> : <Home />}
        />
        <Route path="/register" element={<Register />} />
        <Route path="/pendidikan" element={<Pendidikan />} />
        <Route path="/pelatihan" element={<Pelatihan />} />
        <Route path="/pengalaman" element={<Pengalaman />} />
      </Routes>
    </div>
  );
}

export default App;
