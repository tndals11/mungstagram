import React, { useState } from 'react'
import api from '../api/axios'
import { useNavigate } from 'react-router-dom'

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const {name, value} = e.target;
    setFormData({ ...formData, [name]: value});
  }; 

  const handleLogin = async (e) => {
    e.preventDefault();

    if (isLoading) return;

    if (!formData.username.trim() || !formData.password.trim()) {
      alert('아이디와 비밀번호를 입력해주세요.');
      return;
    }

    setIsLoading(true);

    try {
      const response = await api.post('/api/auth/login', formData);

      if (response.status === 200) {
        const token = response.data.body;
        localStorage.setItem('token', token);

        alert("로그인 성공");
        navigate('/');
      }
    } catch(error) {
      alert(error.msg || '로그인에 실패했습니다.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className='min-h-screen flex items-center justify-center p-4'>
      <div className='bg-white p-8 rounded-2xl shadow-xl w-full max-w-md'>
        <h1 className='text-2xl font-bold text-center text-gray-800 mb-8'>로그인</h1>

        <form className='space-y-6' onSubmit={handleLogin}>
          <div>
            <label className='block text-sm font-medium text-gray-700 mb-1'>아이디</label>
            <input 
              name='username'
              type='text'
              value={formData.username}
              onChange={handleChange}
              placeholder='아이디를 입력해주세요'
              className='w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all'
            />
          </div>
          
          <div>
            <label className='block text-sm font-medium text-gray-700 mb-1'>비밀번호</label>
            <input 
              name='password'
              type='password'
              value={formData.password}
              onChange={handleChange}
              placeholder='비밀번호를 입력해주세요'
              className='w-full px-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all'
            />
          </div>

          <button 
            type='submit'
            disabled={isLoading} 
            className={`w-full py-3 rounded-xl font-bold transition-all mt-7 shadow-lg
              ${isLoading
                ? 'bg-gray-400 cursor-not-allowed'
                : 'bg-blue-600 hover:bg-blue-700 active:scale-95 text-white'
              }`}
          >
            {isLoading ? '처리중...' : '로그인'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login