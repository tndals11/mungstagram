import React from 'react'
import Signup from './pages/Signup'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Layout from './components/Layout'
import Main from './pages/Main'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Main />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} /> 
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App