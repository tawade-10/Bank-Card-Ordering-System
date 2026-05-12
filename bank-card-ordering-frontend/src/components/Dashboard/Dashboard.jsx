// import React, { useEffect, useState } from "react";
// import axios from "axios";
// import { toast } from "react-toastify";
// import { Link, useNavigate } from "react-router-dom";
// import "./Dashboard.css";
//
// import MyCards from "../MyCards/MyCards";
// import MyActiveCards from "../MyActiveCards/MyActiveCards";
// import RecentCardTable from "../RecentCardTable/RecentCardTable";
//
// import Tabs from "@mui/material/Tabs";
// import Tab from "@mui/material/Tab";
// import Box from "@mui/material/Box";
//
// export default function Dashboard() {
//   const [requests, setRequests] = useState([]);
//   const [tabValue, setTabValue] = useState(-1);
//   const navigate = useNavigate();
//   const role = localStorage.getItem("role");
//
//   useEffect(() => {
//     loadRequests();
//   }, []);
//
//   const handleGoBack = () => navigate(-1);
//
//   const handleTabChange = (event, newValue) => setTabValue(newValue);
//
//   const loadRequests = async () => {
//     try {
//       const token = localStorage.getItem("token");
//
//       const url =
//         role === "ADMIN"
//           ? "http://localhost:8080/api/request-card"
//           : "http://localhost:8080/api/request-card/email";
//
//       const response = await axios.get(url, {
//         headers: { Authorization: `Bearer ${token}` },
//       });
//
//       setRequests(response.data);
//     } catch (error) {
//       console.error("Error fetching requests:", error);
// //       alert("Failed to fetch card requests.");
//     }
//   };
//
//   const filteredRequests = requests.filter((req) => {
//     if (tabValue === 1) return req.status === "PENDING";
//     if (tabValue === 2) return req.status === "APPROVED";
//     if (tabValue === 3) return req.status === "REJECTED";
//     return true;
//   });
//
//   const recentTwoRequests = [...requests]
//     .sort((a, b) => new Date(b.updatedAt) - new Date(a.updatedAt))
//     .slice(0, 2);
//
//   return (
//     <div className="dashboard-wrapper">
//       <h2 className="dashboard-title">
//         {role === "ADMIN" ? "Admin Dashboard" : "Dashboard"}
//       </h2>
//
//       <div className="top-boxes">
//         {role === "ADMIN" ? (
//           <Box sx={{ width: "100%" }}>
//             <Tabs
//               value={tabValue}
//               onChange={handleTabChange}
//               centered
//               textColor="tertiary"
//               indicatorColor="primary"
//             >
//               <Tab label="Pending" />
//               <Tab label="Approved" />
//               <Tab label="Rejected" />
//             </Tabs>
//           </Box>
//         ) : (
//           <>
//             <Link to="/dashboard/request-new-card" className="box">
//               Request New Card
//             </Link>
//             <Link to="/dashboard/my-cards" className="box">
//               My Cards
//             </Link>
//             <Link to="/dashboard/track-requests" className="box">
//               Track Requests
//             </Link>
//           </>
//         )}
//       </div>
//
//       <hr className="divider" />
//
//       {role === "ADMIN" ? (
//         <div>
//           <h3 className="section-title">Manage Requests</h3>
//
//           <div className="table-container">
//             <table className="admin-table">
//               <thead>
//                 <tr>
//                   <th>Request ID</th>
//                   <th>Customer Name</th>
//                   <th>Card Type</th>
//                   <th>Variant</th>
//                   <th>Reason</th>
//                   <th>Status</th>
//                   <th>Date</th>
//                   <th>Actions</th>
//                 </tr>
//               </thead>
//
//               <tbody>
//                 {filteredRequests.length > 0 ? (
//                   filteredRequests.map((req) => (
//                     <tr key={req.requestId}>
//                       <td>{req.requestId}</td>
//                       <td>{req.customerName || "N/A"}</td>
//                       <td>{req.cardType}</td>
//                       <td>{req.cardVariant}</td>
//                       <td>{req.reason}</td>
//                       <td>{req.status}</td>
//                       <td>{req.localDate}</td>
//
//                       <td>
//                         <button
//                           className="view-btn"
//                           onClick={() =>
//                             navigate(
//                               `/admin/dashboard/view-request/${req.requestId}`
//                             )
//                           }
//                         >
//                           View
//                         </button>
//
//                         {req.status === "PENDING" && (
//                           <button
//                             className="update-btn"
//                             onClick={() =>
//                               navigate(
//                                 `/admin/dashboard/update-request/${req.requestId}`
//                               )
//                             }
//                           >
//                             Update Status
//                           </button>
//                         )}
//                       </td>
//                     </tr>
//                   ))
//                 ) : (
//                   <tr>
//                     <td colSpan="8" style={{ textAlign: "center" }}>
//                       No Request Found
//                     </td>
//                   </tr>
//                 )}
//               </tbody>
//             </table>
//           </div>
//
//           <button className="btn" onClick={handleGoBack}>
//             Go Back
//           </button>
//         </div>
//       ) : (
//         <>
//           <h3 className="section-title"></h3>
//           <MyActiveCards />
//           <hr className="divider" />
//           <h3 className="section-title">Recent Card Requests</h3>
//           <RecentCardTable requests={recentTwoRequests} />
//         </>
//       )}
//     </div>
//   );
// }

import React, { useEffect } from "react";
import { toast } from "react-toastify";
import { connectWebSocket } from "../../websocket";
import AdminDashboard from "./AdminDashboard/AdminDashboard";
import CustomerDashboard from "./CustomerDashboard/CustomerDashboard";

export default function Dashboard() {

  useEffect(() => {
    const userId = localStorage.getItem("userId");

    if (userId) {
      connectWebSocket(userId, (notification) => {
        toast.info(`${notification.title}: ${notification.message}`);
      });
    }

  }, []);

  const role = localStorage.getItem("role");

  return (
    <div>
      {role === "ADMIN" ? <AdminDashboard /> : <CustomerDashboard />}
    </div>
  );
}