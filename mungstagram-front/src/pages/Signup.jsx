import axios from 'axios';
import React, { useState } from 'react'
import api from '../api/axios';
import { useNavigate } from 'react-router-dom';

function Signup() {
  
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    username: '',
    password: '',
    nickname: '',
  });

  const [errors, setErrors] = useState({});

  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
      const {name, value} = e.target;

      setErrors({ ...errors, [name]: ''})

      setFormData({
        ...formData,
        [name]: value,
      });
  };

const validateForm = () => {
    const newErrors = {};
    
    if (!formData.username.trim()) {
      newErrors.username = '아이디를 입력해주세요';
    }
    if (!formData.password.trim()) {
      newErrors.password = '비밀번호를 입력해주세요';
    }
    if (!formData.nickname.trim()) {
      newErrors.nickname = '별명을 입력해주세요';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    
    if (isLoading || !validateForm()) return;

    setIsLoading(true);

    try {

      const response = await api.post('/api/users/register', formData);
      
      if (response.status === 200 || response.status === 201) {
        alert('회원가입이 완료되었습니다!');

        navigate('/login');
      }

    } catch (error) {
      const { msg } = error;  
  
      if (msg?.includes('아이디')) {
        setErrors({ username: msg });
      } else if (msg?.includes('별명')) {
        setErrors({ nickname: msg });
      } else {
        alert(msg || '오류가 발생했습니다');
      }

    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className='bg-gray-100 min-h-screen flex items-center justify-center p-4'>

      <div className='bg-white p-8 rounded-2xl shadow-xl w-full max-w-md'>
          <h1 className='text-2xl font-bold text-center text-gray-800 mb-8'>회원가입</h1>

          <form className='space-y-6' onSubmit={handleRegister}>
            <div>
              <label className='block text-sm font-medium text-gray-700 mb-1'>아이디</label>
              <input 
                name='username'
                type='text'
                value={formData.username || ''} 
                onChange={handleChange}
                placeholder='아이디를 입력해주세요'
                className={`w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all 
                  ${errors.username ? 'border-red-500' : 'border-gray-300'}`}
            />

              {errors.username && (
                <p className='text-red-500 text-xs mt-1 ml-1 animate-pulse'>
                  {errors.username}
                </p>
              )}
            </div>
            
            <div>
              <label className='block text-sm font-medium text-gray-700 mb-1'>비밀번호</label>
              <input 
                name='password'
                type="password"
                value={formData.password || ''}
                onChange={handleChange}
                placeholder='비밀번호를 입력해주세요.'
                className={`w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all
                  ${errors.password ? 'border-red-500' : 'border-gray-300'}`} 
              />

              {errors.password && (
                <p className='text-red-500 text-xs mt-1 ml-1 animate-pulse'>
                    {errors.password}
                </p>
              )}
            </div>

            <div>
              <label className='block text-sm font-medium text-gray-700 mb-1'>별명</label>
              <input 
                name='nickname'
                type="text"
                value={formData.nickname || ''}
                onChange={handleChange}
                placeholder='비밀번호를 입력해주세요.'
                className={`w-full px-4 py-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 transition-all
                  ${errors.nickname ? 'border-red-500' : 'border-gray-300'}`}
                
               />

              {errors.nickname && (
                <p className='text-red-500 text-xs mt-1 ml-1 animate-pulse'>
                    {errors.nickname}
                </p>
              )}
            </div>
               <button 
                type='submit'
                disabled={isLoading} 
                className={`w-full py-3 rounded-xl font-bold transition-all mt-7 shadow-lg
                  ${isLoading
                    ? 'bg-gray-400 cursor-not-allowed'
                    : 'bg-blue-600 hover:bg-blue-700 active:scale-95 shadow-blue-200 text-white'
                  }`}
                >
                  { isLoading ? "처리중..." : "회원가입"}
                </button>
          </form>
      </div>
    </div>
  )
}

export default Signup