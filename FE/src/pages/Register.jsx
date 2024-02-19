import { useState } from "react";
import { Alert, CardFooter, Form, InputGroup } from "react-bootstrap";
import { Link } from "react-router-dom";
import { API } from "../config/axiosHelper";

function Register() {
  const [message, setMessage] = useState(null);

  // REGIS
  const [formRegister, setFormRegister] = useState({
    full_name: "",
    email: "",
    password: "",
  });

  const handleChangeRegister = (e) => {
    setFormRegister({
      ...formRegister,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmitRegister = async (e) => {
    try {
      e.preventDefault();
      const configregister = {
        headers: {
          "Content-Type": "application/json",
        },
      };

      const body = JSON.stringify(formRegister);

      const response = await API.post("/users", body, configregister);
      console.log("ini regis respon", response);

      const alert = (
        <Alert
          variant="success"
          className="py-3"
          onClose={() => setMessage(null)}
          dismissible
        >
          Registration Success
        </Alert>
      );
      setMessage(alert);

      setFormRegister({
        email: "",
        password: "",
      });
    } catch (error) {
      console.log(error);
      const alert = (
        <Alert variant="danger" className="py-1">
          {error.response.data.errors}
        </Alert>
      );
      setMessage(alert);
    }
  };

  return (
    <div>
      <div className="container mt-4 col-md-6">
        <div className="">
          {message && message}
          <h1>Registration</h1>
          <hr />
          <Form onSubmit={handleSubmitRegister}>
            <InputGroup className="mb-3">
              <Form.Control
                type="email"
                name="email"
                value={formRegister.email}
                onChange={handleChangeRegister}
                className="signupinput"
                placeholder="Email"
              />
            </InputGroup>
            <InputGroup className="mb-3">
              <Form.Control
                type="password"
                name="password"
                value={formRegister.password}
                onChange={handleChangeRegister}
                placeholder="Password"
              />
            </InputGroup>

            <button
              className="bg-dark w-100 border-0 rounded-2 text-light fw-bold p-2"
              type="submit"
            >
              Register
            </button>
          </Form>
          <CardFooter>
            <p className="">
              Already have an account ? Klik{" "}
              <Link to="/" className="border-0 fw-bold">
                Here
              </Link>
            </p>
          </CardFooter>
        </div>
      </div>
    </div>
  );
}

export default Register;
