import React, { useState } from 'react'
import styles from "./Copilot.module.css"
import SmartToyIcon from '@mui/icons-material/SmartToy';
import { GoogleGenerativeAI } from "@google/generative-ai";

function Copilot() {
  const [question, setQuestion] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [showChat, setShowChat] = useState(false);
  const [questionMessage, setQuestionMessage] = useState(null);

  const genAI = new GoogleGenerativeAI("AIzaSyBuGGkUtK3mqCeGBo3STEscCP7Gi-wNT2I");
  const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });
  const prompt = "Explain how AI works";

  const getAnswer = (question) => {
    setQuestion("");
    setAnswer(()=>"Thinking...");
    model
      .generateContent(prompt)
      .then((response) => {
        console.log(response.candidates[0].content);
        console.log(response.candidates[0].content.parts[0].text);
        setAnswer(response.candidates[0].content.parts[0].text);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <div className={styles.drawer}>
      <SmartToyIcon onClick={()=> setShowChat((old)=>!old)}/>
      {showChat && (<div className={styles.chat}>
        <div className={styles.chatContent}>
          <div className={styles.question}>{questionMessage}</div>
          <div className={styles.answer}>{answer}</div>
        </div>
        <div className={styles.chatInput}>
          <input type="text" placeholder="Ask me anything" onChange={(e)=>setQuestion(e.target.value)} value={question}/>
          <button onClick={()=>{getAnswer(); setQuestionMessage(question)}}>Send</button>
        </div>
      </div>)}
    </div>
  )
}

export default Copilot