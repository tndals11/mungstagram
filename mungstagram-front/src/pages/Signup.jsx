import axios from 'axios';
import React, { useState } from 'react'

function Signup() {

  const [formData, setFormData] = useState({
    username: '',
    password: '',
    nickname: '',
  });

  const [erros, setErrors] = useState({
    username: '',
    password: '',
    nickname: '',
  });

  const handleChange = (e) => {
      const {name, value} = e.target;

      setErrors({ ...erros, [name]: ''})

      setFormData({
        ...formData,
        [name]: value,
      });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    
    try {

      const response = await axios.post('http://localhost:8080/api/users/register', formData);
      
      if (response.status === 200 || response.status === 201) {
        alert('회원가입이 완료되었습니다!');
        console.log('서버 응답 데이터 : ', response.data);
      }

    } catch (error) {
        if(error.response && error.response.data) {
          setErrors(error.response.data);
        }
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
                  ${erros.username ? 'border-red-500' : 'border-gray-300'}`}
            />
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
                  ${erros.password ? 'border-red-500' : 'border-gray-300'}`} 
              />
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
                  ${erros.nickname ? 'border-red-500' : 'border-gray-300'}`}
               />

               <button 
                type='submit' 
                className='w-full bg-blue-600 text-white py-3 rounded-xl font-bold hover:bg-blue-700 active:scale-95 transition-transform shadow-lg shadow-blue-200 mt-7'>
                  회원가입
                </button>
            </div>
          </form>
      </div>
    </div>
  )
}

export default Signup