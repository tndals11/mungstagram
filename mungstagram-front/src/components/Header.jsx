import { UserCircle } from 'lucide-react';
import React, { useEffect, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'

function Header() {
  const navigate = useNavigate();

  const [token, setToken] = useState(localStorage.getItem('token'));

  const location = useLocation();

  useEffect(() => {
    const currentToken = localStorage.getItem('token');
    setToken(currentToken);

    alert(currentToken);
  }, [location])

  const handelLogout = () => {
    localStorage.removeItem('accessToken');
    alert('로그아웃 되었습니다.');
    navigate('/login');
  };

  return (
    <header className='bg-white shadow-md sticky top-0 z-50 w-full'>
      
      <div className='max-w-[1280px] mx-auto px-6 py-4 flex justify-between items-center'>
        
        <Link to="/" className="flex items-center gap-2">
          <span className='text-xl'>🐾</span>
          <h1 className='text-xl font-black text-orange-500 tracking-tighter'>
            PET <span className='text-black'>ARCHIVE</span>
          </h1>
        </Link>

        <nav className='flex items-center space-x-8'>
          <Link to="/posts" className="text-sm text-black hover:text-orange-400 transition-colors">
            펫등록
          </Link>
          <Link to="/posts" className="text-sm text-black hover:text-orange-400  transition-colors">
            댕시판
          </Link>
          <Link to="/posts" className="text-sm text-black hover:text-orange-400  transition-colors">
            이벤트
          </Link>
        </nav>

        <nav className='flex items-center space-x-8'>
          <div className='flex items-center space-x-3 pl-4 border-gray-200'>
            {token ? (
              <div className='flex items-center sapce-x-5'>
                <Link
                  to="/profile"
                  className='flex items-center gap-1.5 text-black'
                >
                  <span className='text-sm font-medium hover:text-orange-500 transition-colors'>마이페이지</span>
                </Link>
                <span className='ml-2 mr-2 text-gray-200 font-thin'>|</span>
                <span
                  onClick={handelLogout}
                  className='text-sm font-medium cursor-pointer hover:text-red-600 transition-colors'
                >
                  로그아웃
                </span>
              </div>
            ) : (
              <div className='flex items-center sapce-x-4'>
                <Link to="/login" className='text-sm font-medium text-black hover:text-orange-500'>
                  로그인
                </Link>
                <span className='ml-2 mr-2 text-gray-200 font-thin'>|</span>
                <Link to="/signup" className='text-sm font-medium text-black hover:text-orange-500 transition-colors'>
                  회원가입
                </Link>
              </div>
            )}
          </div>
        </nav>

      </div>

    </header>
  )
}

export default Header