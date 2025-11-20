import React from 'react'
import 'remixicon/fonts/remixicon.css'
import "../css/QuestionItem.css";
const QuestionItem = (props) => {
  return (
    <div className='question-item'>
        <div className='question'>
            <div><i class="ri-questionnaire-line"></i>{props.question}</div>
        </div>
        <div className='answer'>
            <div>{props.answer || "Đang đợi phản hồi"}<i class="ri-reply-fill"></i></div>
        </div>
    </div>
  )
}

export default QuestionItem