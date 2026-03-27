// import React, { useContext } from "react";
// import { Navigate } from "react-router-dom";
// import { userContext } from "../ContextProvider";
//
// const ProtectedRoutes = ({ allowedRoles, children }) => {
//   const { role, authenticated } = useContext(userContext);
//
//   if (!authenticated) return <Navigate to="/" />;
//
//   if (allowedRoles && !allowedRoles.includes(role))
//     return <Navigate to="/not-authorized" />;
//
//   return children;
// };
//
// export default ProtectedRoutes;





import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoutes = ({ children }) => {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/" />;
};

export default ProtectedRoutes;