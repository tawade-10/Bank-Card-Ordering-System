import { Link } from "react-router-dom";
import "./AdminSidebar.css";

export default function AdminSidebar() {
  return (
    <div className="sidebar">
      <h2>Admin Panel</h2>
      <Link to="/admin/dashboard">
        Dashboard
      </Link>
      <Link to="/admin/card-requests">
        Card Requests
      </Link>
      <Link to="/admin/account-requests">
        Account Requests
      </Link>
      <Link to="/admin/customers">
        Customers
      </Link>
      <Link to="/">
        Logout
      </Link>
    </div>
  );
}