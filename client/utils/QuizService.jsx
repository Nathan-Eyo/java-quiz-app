import axios from "axios"

export const api = axios.create({
    baseURL: "http://localhost:8080/api/quizzes"
})
console.log("token",localStorage.getItem('token'))

api.interceptors.request.use(
    (config) => {
        // Get the token from localStorage
        const token = localStorage.getItem('token');
        
        if (token) {
            // If token exists, set the Authorization header
            config.headers['Authorization'] = `Bearer ${token}`;
            
        }

        return config;
    },
    (error) => {
        // Handle request error
        return Promise.reject(error);
    }
);

export const createQuestion = async(quizQuestion) =>{
    try {
        const res = await api.post("/create-new-question", quizQuestion)
        return res.data
    } catch (error) {
        console.error(error)
    }
}

export const getAllQuestions = async() =>{
    try {
        const res = await api.get("/all-questions")
        return res.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const fetchQuizForUser = async(number, subject, difficulty) =>{
    try {
        const res = await api.get(
            `/quiz/fetch-questions-for-user?numOfQuestion=${number}&subject=${subject}&difficulty=${difficulty}`)
    return res.data
    } catch (error) {
        console.error(error)
    }
}

export const getAllSubjects = async() =>{
    try {
        const res = await api.get("/subjects")
        return res.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const getAllDifficulty = async() =>{
    try {
        const res = await api.get("/difficulty")
        return res.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const updateQuestion = async(id, question) =>{
    try {
        const res = api.put(`/question/${id}/update`, question)
        return res.data
    } catch (error) {
        console.error(error)
    }
}

export const getQuestionById = async(id) =>{
    try {
        const res = await api.get(`/question/${id}`)
        return res.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const deleteQuestion = async(id) =>{
    try {
        const res = api.delete(`/question/${id}/delete`)
        return res.data
    } catch (error) {
        console.error(error)
    }
}