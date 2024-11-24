import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import UserService from "../service/UserService";

function Navbar() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    UserService.isAuthenticated()
  );
  const [isAdmin, setIsAdmin] = useState(UserService.isAdmin());
  const navigate = useNavigate();

  useEffect(() => {
    const handleAuthChange = () => {
      setIsAuthenticated(UserService.isAuthenticated());
      setIsAdmin(UserService.isAdmin());
    };

    window.addEventListener("authChange", handleAuthChange);

    return () => {
      window.removeEventListener("authChange", handleAuthChange);
    };
  }, []);

  const handleLogout = () => {
    const confirmDelete = window.confirm(
      "Are you sure you want to logout this user?"
    );
    if (confirmDelete) {
      UserService.logout();
      const event = new Event("authChange");
      window.dispatchEvent(event);
      navigate("/");
    }
  };

  return (
    <nav>
      <ul>
        {!isAuthenticated && (
          <li>
            <Link to="/">User Link</Link>
          </li>
        )}
        {isAuthenticated && (
          <li>
            <Link to="/profile">Profile</Link>
          </li>
        )}
        {isAdmin && (
          <li>
            <Link to="/admin/user-management">User Management</Link>
          </li>
        )}
        {isAuthenticated && (
          <li>
            <Link to="/" onClick={handleLogout}>
              Logout
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
}

export default Navbar;
