import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../config/axiosHelper";
import Alert from "react-bootstrap/Alert";
import { UserContext } from "../context/userContext";
import { CardFooter, Form, InputGroup } from "react-bootstrap";

function Login() {
  const [state, dispatch] = useContext(UserContext);
  const [messageL, setMessageL] = useState(null);
  const navigate = useNavigate();

  const checkAuth = () => {
    console.log("LOGIN :", state.isLogin);
    if (state.isLogin === true) {
      navigate("/home");
    }
  };

  checkAuth();

  const [formLogin, setFormLogin] = useState({
    email: "",
    password: "",
  });

  const handleLoginChange = (e) => {
    setFormLogin({
      ...formLogin,
      [e.target.name]: e.target.value,
    });
  };

  async function login(event) {
    try {
      event.preventDefault();
      const configlogin = {
        headers: {
          "Content-Type": "application/json",
        },
      };

      const bodylogin = JSON.stringify(formLogin);
      const response = await API.post("/auth/login", bodylogin, configlogin);

      console.log("response :".response);

      dispatch({
        type: "LOGIN_SUCCESS",
        payload: response.data.data,
      });
    } catch (errors) {
      console.log("err :", errors);
      const alertLogin = (
        <Alert
          variant="danger"
          className="py-3"
          onClose={() => setMessageL(null)}
          dismissible
        >
          {errors.response.data.errors
            ? errors.response.data.errors
            : "Error to login"}
        </Alert>
      );
      setMessageL(alertLogin);
    }
  }

  return (
    <div>
      <div className="container mt-4 col-md-6">
        <div className="">
          {messageL && messageL}
          <h1>Login</h1>
          <hr />
          <div className="col-md-12 ">
            <Form onSubmit={login}>
              <InputGroup className="mb-3">
                <Form.Control
                  type="email"
                  className="signupinput"
                  placeholder="Email"
                  onChange={handleLoginChange}
                  name="email"
                  value={formLogin.email}
                />
              </InputGroup>
              <InputGroup className="mb-3">
                <Form.Control
                  type="password"
                  className="signupinput"
                  placeholder="Password"
                  onChange={handleLoginChange}
                  name="password"
                  value={formLogin.password}
                />
              </InputGroup>
              <button
                className="bg-dark w-100 border-0 rounded-2 text-light fw-bold p-2 "
                type="submit"
              >
                Login
              </button>
            </Form>
            <CardFooter>
              <p className="">
                Do not have an account yet? Klik{" "}
                <Link className="border-0 fw-bold" to="/register">
                  Here
                </Link>
              </p>
            </CardFooter>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
