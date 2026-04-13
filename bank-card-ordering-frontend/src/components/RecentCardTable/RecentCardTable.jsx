import React from "react";
import "./RecentCardTable.css";

export default function RecentCardTable({ requests }) {
  return (
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
          {requests.length > 0 ? (
            requests.map((req) => (
              <tr key={req.requestId}>
                <td>{req.requestId}</td>
                <td>{req.cardType}</td>
                <td>{req.status}</td>
                <td>{req.localDate}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" style={{ textAlign: "center" }}>
                No requests found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}