import React from 'react'
import Header from './Header'
import { Outlet } from 'react-router-dom'

function Layout() {
  return (
    <div className='flex flex-col min-h-screen'>
      <Header /> 
    
      <main className='flex-grow'>
        <div className='max-w-[1280px] mx-auto w-full px-4 py-8'>
          <Outlet />
        </div>
      </main>
        
      <footer /> 
    </div>
  )
}

export default Layout