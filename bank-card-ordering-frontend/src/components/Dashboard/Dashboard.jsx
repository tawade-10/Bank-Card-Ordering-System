import React from 'react';
import './Dashboard.css';

export default function Dashboard() {
  return (
     <div className="dashboard-wrapper">
          <div className="top-boxes">
            <div className="box">Request New Card</div>
            <div className="box">My Cards</div>
            <div className="box">Track Requests</div>
          </div>
        </div>
  );
}