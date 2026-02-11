import React, { useEffect, useState } from 'react'
import api from "../../api/axios"; 

function DogCard() {
  const [pets, setPets] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchPets();
  }, []);

  const fetchPets = async () => {
    setIsLoading(true);

    try {
      const response = await api.get('/api/pets');

      console.log(response);

      if(response.data.status === 200) {
        setPets(response.data.body);
      }     

    } catch (error) {
      console.error('강아지 목록 불러오기 실패 :', error.msg);
      alert(error.msg || '강아지 목록을 불러오는데 실패했습니다');
    } finally {
      setIsLoading(false);
    }
  };

   if (isLoading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className='bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-xl transition-all hover:translate-y-2 cursor-pointer'>
      <img
       src={dog.profileImage}
       alt={dog.name}
       className='w-full h-full object-cover'
      />
      <p>{dog.name}</p>
    </div>
  )
}

export default DogCard