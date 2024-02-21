import React from 'react'
import "./UserDashboard.css"
import Sidebar from "../../../Components/Sidebar/Sidebar";
import {userItems} from "../../../Data/UserSidebarData";
import {Route, Routes} from "react-router-dom";
import Home from "../../Home";
import Contact from "../../Contact";

const UserDashboard = () => {
    return (
        <div className="user-dashboard-container">
            <Sidebar items={userItems}/>

            <div className="dashboard-content">
                <Routes>
                    <Route path="/" element={<Contact/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default UserDashboard