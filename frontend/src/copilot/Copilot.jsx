import React, { useState } from 'react'
import styles from "./Copilot.module.css"
import SmartToyIcon from '@mui/icons-material/SmartToy';

function Copilot() {
  const [question, setQuestion] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [showChat, setShowChat] = useState(false);

  return (
    <div className={styles.drawer}>
      <SmartToyIcon onClick={()=> setShowChat((old)=>!old)}/>
      {showChat && (<div className={styles.chat}>
        <div className={styles.chatContent}>
          <div className={styles.question}>{question}</div>
          <div className={styles.answer}>{answer}</div>
        </div>
        <div className={styles.chatInput}>
          <input type="text" placeholder="Ask me anything" onChange={(e)=>setQuestion(e.target.value)}/>
          <button onClick={()=>setAnswer("I am a copilot")}>Send</button>
        </div>
      </div>)}
    </div>
  )
}

export default Copilot