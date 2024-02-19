import { Alert, FloatingLabel } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import { dataPelamar as dummyDataPelamar } from "../dummy/dataPelamar";
import { API } from "../config/axiosHelper";
import { useEffect, useRef, useState } from "react";
import NavbarUser from "../components/NavbarUser";

function Home() {
  const [biodata, setBiodata] = useState({});
  const [message, setMessage] = useState();
  const scrollToTopRef = useRef(null);

  const getBiodata = async () => {
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };
      const response = await API.get("/users/current", config);
      setBiodata(response.data.data);
      // console.log(response.data.data);
    } catch (error) {
      // console.log(error);
    }
  };

  useEffect(() => {
    getBiodata();
  }, []);

  const handleChange = (e, fieldName) => {
    const selectedValue = e.target.value;

    setBiodata((prevBiodata) => ({
      ...prevBiodata,
      [fieldName]: selectedValue,
    }));
    // console.log(biodata);
  };

  useEffect(() => {
    if (message !== null) {
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  }, [message]);

  const { role, ...biodataWithoutRole } = biodata;

  const handleEditeBiodata = async (e) => {
    try {
      e.preventDefault();
      // console.log("biodataWithoutRole :", biodataWithoutRole);
      // console.log("token :", localStorage.token);
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.patch(
        "/users/current",
        biodataWithoutRole,
        config
      );

      let alert = "";
      if (response.status === 200) {
        alert = (
          <Alert
            variant="success"
            className="py-3"
            onClose={() => setMessage(null)}
            dismissible
          >
            Data Updated
          </Alert>
        );

        getBiodata();
      } else {
        alert = (
          <Alert
            variant="danger"
            className="py-3"
            onClose={() => setMessage(null)}
            dismissible
          >
            Error
          </Alert>
        );
      }
      setMessage(alert);
    } catch (error) {
      console.log(error);
      const alert = (
        <Alert
          variant="danger"
          className="py-3"
          onClose={() => setMessage(null)}
          dismissible
        >
          {error.response.data.errors}
        </Alert>
      );
      setMessage(alert);
    }
  };

  return (
    <div>
      <NavbarUser />
      <div className="container mt-4">
        <div className="row">
          <div ref={scrollToTopRef} className="col-lg-10 mx-auto">
            <h1 className="mb-3">Data Pribadi Pelamar</h1>
            <hr />
            {message && message}
            <Form onSubmit={handleEditeBiodata}>
              {dummyDataPelamar.map(({ text, type, name, option }) => {
                switch (type) {
                  case "text":
                    return (
                      <Form.Floating key={name} className="mb-3">
                        <Form.Control
                          id={name}
                          type="text"
                          placeholder=""
                          value={biodata[name] && biodata[name]}
                          onChange={(e) => handleChange(e, name)}
                        />
                        <label htmlFor={name}>{text}</label>
                      </Form.Floating>
                    );
                  case "textarea":
                    return (
                      <FloatingLabel
                        className="mb-3"
                        key={name}
                        controlId="floatingTextarea2"
                        label={text}
                      >
                        <Form.Control
                          as="textarea"
                          placeholder="Leave a comment here"
                          style={{ height: "100px" }}
                          value={biodata[name] && biodata[name]}
                          onChange={(e) => handleChange(e, name)}
                        />
                      </FloatingLabel>
                    );
                  case "dropdown":
                    return (
                      <FloatingLabel
                        key={name}
                        controlId="floatingSelect"
                        label={text}
                        className="mb-3"
                      >
                        <Form.Select
                          aria-label="Floating label select example"
                          name={name}
                          value={biodata[name] && biodata[name]}
                          onChange={(e) => handleChange(e, name)}
                        >
                          <option>Open this select menu</option>
                          {option.map((ops) => {
                            return (
                              <option key={ops} value={ops}>
                                {ops}
                              </option>
                            );
                          })}
                        </Form.Select>
                      </FloatingLabel>
                    );
                  case "email":
                    return (
                      <Form.Floating className="mb-3" key={name}>
                        <Form.Control
                          id={name}
                          type="email"
                          placeholder="name@example.com"
                          value={biodata[name] && biodata[name]}
                          onChange={(e) => handleChange(e, name)}
                          required
                        />
                        <label className="" htmlFor={name}>
                          {text}
                        </label>
                      </Form.Floating>
                    );

                  default:
                    break;
                }
              })}
              <div className="row mx-auto">
                <button
                  className="bg-dark col-lg-3 border-0 rounded-2 text-light fw-bold p-3 my-3"
                  type="submit"
                >
                  Submit
                </button>
              </div>
            </Form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
