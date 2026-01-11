import React from "react";
import "./ContactButton.css";

const ContactButton = ({ onClick }) => {
  return (
    <button className="contact-button" onClick={onClick} title="Tư vấn">
      <div className="contact-button-icon">
        <img 
          src="/assets/tư vấn.png" 
          alt="Tư vấn" 
          className="contact-icon-image"
        />
      </div>
      <span className="contact-button-text">Tư vấn</span>
    </button>
  );
};

export default ContactButton;

