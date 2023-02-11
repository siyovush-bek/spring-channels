import React from "react";
import ChannelsLink from "./ChannelLink";

export default function ChannelsList() {
    return (
        <nav className="col-md-2 d-none d-md-block bg-light sidebar">
          <div className="sidebar-sticky">
            <ul className="nav flex-column">
              <li className="nav-item">
                <ChannelsLink 
                    current={true} 
                    name = "1st Channel"
                    link = "#"
                />
              </li>
              <li className="nav-item">
                <ChannelsLink 
                    name = "2nd Channel"
                    link = "#"
                />
              </li>
            </ul>
          </div>
        </nav>
    )
}