import React, { createContext, useState, useEffect } from "react";

export const userContext = createContext();

const ContextProvider = ({ children }) => {
  const [role, setRole] = useState(null);
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const savedRole = localStorage.getItem("role");

    if (token && savedRole) {
      setAuthenticated(true);
      setRole(savedRole);
    }
  }, []);

  return (
    <userContext.Provider value={{ role, authenticated }}>
      {children}
    </userContext.Provider>
  );
};

export default ContextProvider;