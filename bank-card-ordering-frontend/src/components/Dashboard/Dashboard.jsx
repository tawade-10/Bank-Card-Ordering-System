import React from 'react';
import './Dashboard.css';
import { Link } from "react-router-dom";
import { TbCreditCardPay } from "react-icons/tb";
import { CiCreditCard2 } from "react-icons/ci";

export default function Dashboard() {

    const role = localStorage.getItem("role");

  return (
    <div className="dashboard-wrapper">
      <div className="top-boxes">
        <Link to="/dashboard/request-new-card" className="box"><TbCreditCardPay /> Request New Card</Link>
        <Link to="/dashboard/my-cards" className="box"><CiCreditCard2 /> My Cards</Link>
        <Link to="/dashboard/track-requests" className="box">Track Requests</Link>
      </div>

      <hr className="divider" />
      <h3 className="section-title">My Active Cards</h3>
      <hr className="divider" />
      <h3 className="section-title">Recent Card Requests</h3>
       <div className="table-container">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>Request ID</th>
                    <th>Card Type</th>
                    <th>Status</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>1</td>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                  </tr>
                  <tr>
                    <td>2</td>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                  </tr>
                  <tr>
                    <td>3</td>
                    <td>John</td>
                    <td>Doe</td>
                    <td>@social</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <hr className="divider" />
    </div>
  );
}