import React, { useContext, useEffect, useRef, useState } from 'react'
import styles from "./Copilot.module.css"
import SmartToyIcon from '@mui/icons-material/SmartToy';
import { GoogleGenerativeAI } from "@google/generative-ai";
import { PageContext } from '../PageContext';

function Copilot() {
  const [question, setQuestion] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [showChat, setShowChat] = useState(false);
  const [questionMessage, setQuestionMessage] = useState(null);
  const {divRef} = useContext(PageContext);

  const genAI = new GoogleGenerativeAI("AIzaSyBuGGkUtK3mqCeGBo3STEscCP7Gi-wNT2I");
  const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });
  const systemPrompt = "You are a call center for parking platform website and you should help user and answer his questions of the opened page.\n You will get the content of html page and user question. You should generate the answer for the user question based on the content of the page.\n\n";
  const getAnswer = (question) => {
    let page_content = divRef.current.getHTML();
    console.log(page_content);

    let prompt = systemPrompt +
    "\n\NUser question: " + question 
    + "\n\n:Page HTML Content: \n" + divRef.current.getHTML();

    setQuestion("");
    setAnswer(()=>"Thinking...");
    model
      .generateContent(prompt)
      .then((response) => {
        setAnswer(response.response.candidates[0].content.parts[0].text);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  useEffect(() => {
    if (divRef.current) {
      console.log(divRef.current); // Access the outerHTML of the div
      console.log(divRef.current.innerHTML); // Access the innerHTML of the div
    }
  }, []); // Runs once after the component mounts


  return (
    <div className={styles.drawer}>
      <SmartToyIcon onClick={()=> setShowChat((old)=>!old)}/>
      {showChat && (<div className={styles.chat}>
        <div className={styles.chatContent}>
         {questionMessage && <div className={styles.question}>{questionMessage}</div>}
          {answer && <div className={styles.answer}>{answer}</div>}
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