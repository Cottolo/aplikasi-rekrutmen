import { useContext } from "react";
import { Navbar, Container, Nav, NavDropdown, Button } from "react-bootstrap";
import { UserContext } from "../context/userContext";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../config/axiosHelper";

function NavbarAdmin() {
  const [state, dispatch] = useContext(UserContext);
  const navigate = useNavigate();

  const logoutUser = async () => {
    try {
      const config = {
        headers: {
          "X-API-TOKEN": localStorage.token,
        },
      };
      const response = await API.delete("/auth/logout", config);
      console.log(response.data);

      dispatch({
        type: "LOGOUT",
      });
      navigate("/"); // Data yang diterima dari server
    } catch (error) {
      console.error("Error during logout:", error);
    }
  };
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Link className="text-decoration-none text-dark" to="/home">
          Applikasi Rekrutmen
        </Link>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
          <Nav className="">
            <Nav.Link>
              <Link className="text-decoration-none text-dark" to="/home">
                Home
              </Link>
            </Nav.Link>
            {/* <NavDropdown title="Detail" id="basic-nav-dropdown">
              <NavDropdown.Item href="#action/3.1">
                <Link
                  className="text-decoration-none text-dark"
                  to="/pendidikan"
                >
                  Pendidikan Terakhir
                </Link>
              </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.1">
                <Link
                  className="text-decoration-none text-dark"
                  to="/pelatihan"
                >
                  Riwayat Pelatiahan
                </Link>
              </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.1">
                <Link
                  className="text-decoration-none text-dark"
                  to="/pengalaman"
                >
                  Riwayat Pekerjaan
                </Link>
              </NavDropdown.Item>
            </NavDropdown> */}
            <Button
              onClick={logoutUser}
              className="mt-3 mt-lg-0 ms-lg-2"
              variant="outline-dark"
            >
              Logout
            </Button>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavbarAdmin;
