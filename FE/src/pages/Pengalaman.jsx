import {
  Alert,
  Button,
  FloatingLabel,
  Form,
  Modal,
  Table,
} from "react-bootstrap";
import { pengalamanInput } from "../dummy/dataPengalaman";
import NavbarUser from "../components/NavbarUser";
import { API } from "../config/axiosHelper";
import { useEffect, useState } from "react";

function Pengalaman() {
  const [pengalaman, setPengalaman] = useState({});
  const [message, setMessage] = useState();
  const [showEdite, setShowEdite] = useState(false);
  const [listPengalaman, setListPengalaman] = useState([]);

  const closeEdite = () => {
    setShowEdite(!showEdite);
    setDataModalEdite({});
    setIdEdite();
  };

  const handleChange = (e, fieldName) => {
    const selectedValue = e.target.value;

    setPengalaman((prev) => ({
      ...prev,
      [fieldName]: selectedValue,
    }));
    // console.log("pengalaman change :", pengalaman);
  };
  const handleSubmit = async (e) => {
    try {
      e.preventDefault();
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.post("/pengalaman", pengalaman, config);

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
      // setPengalaman({});
      getPengalaman();
    } catch (error) {
      // console.log(error);
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

  const getPengalaman = async () => {
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };
      const response = await API.get("/pengalaman", config);
      setListPengalaman(response.data.data);
      // console.log("list pengalaman :", response.data.data);
    } catch (error) {
      // console.log("list pengalaman error :", error);
    }
  };

  useEffect(() => {
    getPengalaman();
  }, []);

  // EDIT ===============================================================================
  const [dataModalEdite, setDataModalEdite] = useState({});
  const [idEdite, setIdEdite] = useState();

  const handleChangeEdite = (e, fieldName) => {
    const selectedValue = e.target.value;

    setDataModalEdite((prevData) => ({
      ...prevData,
      [fieldName]: selectedValue,
    }));
    // console.log(dataModalEdite);
  };
  const handleEditePengalaman = async (e) => {
    try {
      e.preventDefault();
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.put(
        `/pengalaman/${idEdite}`,
        dataModalEdite,
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
        closeEdite();
        getPengalaman();
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
      // console.log("errpr :", error);
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

  // DELETE =====================================================================================
  async function handleDelete(e, id) {
    e.preventDefault();
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.delete(`/pengalaman/${id}`, config);

      let alert = "";
      if (response.status === 200) {
        alert = (
          <Alert
            variant="success"
            className="py-3"
            onClose={() => setMessage(null)}
            dismissible
          >
            Data Deleted
          </Alert>
        );

        getPengalaman();
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
      const alert = (
        <Alert
          variant="danger"
          className="py-3"
          onClose={() => setMessage(null)}
          dismissible
        >
          {error.response.data.errors}?{error.response.data.errors}:Error
        </Alert>
      );
      setMessage(alert);
    }
  }

  return (
    <div>
      <NavbarUser />
      <div className="container mt-4">
        <div className="row ">
          <div className="mx-auto col-lg-10">
            <h1 className="mb-3">Data Pengalaman Pelamar</h1>
            <hr />
            {message && message}
            <Form onSubmit={handleSubmit}>
              {pengalamanInput.map(({ name, type, text, option }) => {
                switch (type) {
                  case "text":
                    return (
                      <Form.Floating key={name} className="mb-3">
                        <Form.Control
                          id={name}
                          type="text"
                          placeholder=""
                          value={pengalaman[name] && pengalaman[name]}
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
                          value={pengalaman[name] && pengalaman[name]}
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
                          value={pengalaman[name] && pengalaman[name]}
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
                          value={pengalaman[name] && pengalaman[name]}
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
                  className="bg-dark col-12 col-lg-3 w-lg-25 border-0 rounded-2 text-light fw-bold p-3 my-3"
                  type="submit"
                >
                  Submit
                </button>
              </div>
            </Form>
          </div>
        </div>

        <Table bordered hover className="mt-4" responsive>
          <thead>
            <tr>
              <th>No.</th>
              {pengalamanInput.map(({ text }, index) => (
                <th className="text-center text-nowrap" key={index}>
                  {text}
                </th>
              ))}
              <th className="text-center text-nowrap" colSpan={2}>
                Action
              </th>
            </tr>
          </thead>
          <tbody>
            {listPengalaman?.map((item, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{item.namaPerusahaan}</td>
                <td>{item.posisiTerakhir}</td>
                <td>{item.pendapatanTerakhir}</td>
                <td>{item.tahun}</td>
                <td>
                  <Button
                    onClick={() => {
                      setDataModalEdite(item);
                      setIdEdite(item.id);
                      setShowEdite(!showEdite);
                    }}
                    className="m-auto"
                  >
                    Edit
                  </Button>
                </td>
                <td>
                  <Button
                    onClick={(e) => handleDelete(e, item.id)}
                    className="m-auto"
                    variant="danger"
                  >
                    delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
      <Modal
        size="md"
        aria-labelledby="contained-modal-title-vcenter"
        centered
        show={showEdite}
        onHide={closeEdite}
      >
        <Modal.Header closeButton>
          <Modal.Title className="mx-3 mt-3" id="contained-modal-title-vcenter">
            <p className="text-dark fw-bold">Edite Data Pengalaman</p>
          </Modal.Title>
        </Modal.Header>
        <Modal.Body className="container">
          <Form onSubmit={handleEditePengalaman}>
            {pengalamanInput.map(({ name, type, text, option }) => {
              switch (type) {
                case "text":
                  return (
                    <Form.Floating key={name} className="mb-3">
                      <Form.Control
                        id={name}
                        type="text"
                        placeholder=""
                        value={dataModalEdite[name] && dataModalEdite[name]}
                        onChange={(e) => handleChangeEdite(e, name)}
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
                        value={dataModalEdite[name] && dataModalEdite[name]}
                        onChange={(e) => handleChangeEdite(e, name)}
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
                        value={dataModalEdite[name] && dataModalEdite[name]}
                        onChange={(e) => handleChangeEdite(e, name)}
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
                        value={dataModalEdite[name] && dataModalEdite[name]}
                        onChange={(e) => handleChangeEdite(e, name)}
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
            {/* {pengalamanInput.map(({ name, type, text }) => {
              return (
                <Form.Floating key={name} className="mb-3">
                  <Form.Control
                    id={name}
                    type={type}
                    placeholder=""
                    value={dataModalEdite[name] && dataModalEdite[name]}
                    onChange={(e) => handleChangeEdite(e, name)}
                  />
                  <label htmlFor={name}>{text}</label>
                </Form.Floating>
              );
            })} */}

            <button
              className="bg-dark w-100 border-0 rounded-2 text-light fw-bold p-2 "
              type="submit"
            >
              Save
            </button>
          </Form>
        </Modal.Body>
      </Modal>
    </div>
  );
}

export default Pengalaman;
