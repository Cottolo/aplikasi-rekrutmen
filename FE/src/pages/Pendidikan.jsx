import { Alert, Button, Form, Modal, Table } from "react-bootstrap";
import { pendidikanInput } from "../dummy/dataPendidikan";
import NavbarUser from "../components/NavbarUser";
import { API } from "../config/axiosHelper";
import { useEffect, useState } from "react";

function Pendidikan() {
  const [pendidikan, setPendidikan] = useState({});
  const [message, setMessage] = useState();
  const [showEdite, setShowEdite] = useState(false);
  const [listPendidikan, setListPendidikan] = useState([]);

  const closeEdite = () => {
    setShowEdite(!showEdite);
    setDataModalEdite({});
    setIdEdite();
  };

  const handleChange = (e, fieldName) => {
    const selectedValue = e.target.value;

    setPendidikan((prev) => ({
      ...prev,
      [fieldName]: selectedValue,
    }));
    // console.log("pendidikan change :", pendidikan);
  };
  const handleSubmit = async (e) => {
    try {
      e.preventDefault();
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.post("/pendidikan", pendidikan, config);

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
      // setPendidikan({});
      getPendidikan();
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

  const getPendidikan = async () => {
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };
      const response = await API.get("/pendidikan", config);
      setListPendidikan(response.data.data);
      // console.log("list pendidikan :", response.data.data);
    } catch (error) {
      // console.log("list pendidikan error :", error);
    }
  };

  useEffect(() => {
    getPendidikan();
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
  const handleEditePendidikan = async (e) => {
    try {
      e.preventDefault();
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };

      const response = await API.put(
        `/pendidikan/${idEdite}`,
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
        getPendidikan();
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

      const response = await API.delete(`/pendidikan/${id}`, config);

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

        getPendidikan();
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
            <h1 className="mb-3">Data Pendidikan Pelamar</h1>
            <hr />
            {message && message}
            <Form onSubmit={handleSubmit}>
              {pendidikanInput.map(({ name, type, text }) => {
                return (
                  <Form.Floating key={name} className="mb-3">
                    <Form.Control
                      id={name}
                      type={type}
                      placeholder=""
                      // value={pendidikan[name] && pendidikan[name]}
                      onChange={(e) => handleChange(e, name)}
                    />
                    <label htmlFor={name}>{text}</label>
                  </Form.Floating>
                );
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
              {pendidikanInput.map(({ text }, index) => (
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
            {listPendidikan?.map((item, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{item.jenjangPendidikan}</td>
                <td>{item.namaInstitusi}</td>
                <td>{item.jurusan}</td>
                <td>{item.tahunLulus}</td>
                <td>{item.ipk}</td>
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
            <p className="text-dark fw-bold">Edite Data Pendidikan</p>
          </Modal.Title>
        </Modal.Header>
        <Modal.Body className="container">
          <Form onSubmit={handleEditePendidikan}>
            {pendidikanInput.map(({ name, type, text }) => {
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
            })}

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

export default Pendidikan;
