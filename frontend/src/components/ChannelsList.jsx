import React from "react";
import ChannelsLink from "./ChannelLink";
import { Link } from "react-router-dom";

export default function ChannelsList() {
    return (
        <nav className="col-md-2 d-none d-md-block bg-light sidebar">
          <div className="sidebar-sticky">
            <ul className="nav flex-column">
              <li className="nav-item">
                <Link to="/channel1">channel1</Link>
              </li>
              <li className="nav-item">
                <Link to="/channel2">channel2</Link>
              </li>
            </ul>
          </div>
        </nav>
    )
}