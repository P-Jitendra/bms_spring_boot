import React, { useEffect } from "react";
import style from "../common.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useState } from "react";

function LendCart(props) {
  const navigate = useNavigate();
  const userInfo = props.userInfo;
  let [data, setData] = useState([]);
  const host = window.APP_CONFIG.API_HOST;
  const baseUrl = host + "/api/";
  const [selectedList, setSelectedList] = useState(new Set());
  const [active, setActive] = useState(false);
  useEffect(() => {
    axios
      .get(`${baseUrl}cart/add-to-cart-books?userId=${userInfo.userId}`, {
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        console.log(`Response: ${JSON.stringify(response.data.booksList)}`);
        const cartBookIdArr = response.data.booksList;
        const bookIdList = cartBookIdArr.map(function (params) {
          return params.bookId;
        });
        return axios.post(`${baseUrl}books/get-book-info`, {
          book_id_list: bookIdList,
        });
      })
      .then((response2) => {
        console.log(`Response: ${JSON.stringify(response2.data.booksList)}`);
        const res = response2.data.booksList;
        setData(res);
        // const newSet = new Set();
        // res.forEach((ele) => {
        //   if (selectedList.has(ele.BookId)) {
        //     newSet.add(ele.BookId);
        //   }
        // });
        // setSelectedList(newSet);
        setSelectedList(new Set());
      })
      .catch((error) => {
        console.log(error);
      });
  }, [active, userInfo.userId, baseUrl]);
  const sendLendRequest = (list) => {
    const newList = [];
    list.forEach((ele) => {
      const obj = {
        userId: userInfo.userId,
        bookId: ele,
      };
      newList.push(obj);
    });
    console.log(newList);
    const payload = {
      lend_data: newList,
    };
    // axios
    //   .post(
    //     `${baseUrl}borrow-books`,
    //     payload
    //     // {
    //     //   headers: {
    //     //     "content-type": "application/json",
    //     //   },
    //     //   responseType: "json",
    //     //   withCredentials: true,
    //     // }
    //   )
    //   .then((response) => {
    //     console.log(response.status, response.data);
    //     setActive(!active);
    //   });
    axios
      .put(`${baseUrl}cart/borrow-books`, payload, {
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        setActive(!active);
        console.log(response);
      })
      .catch((err) =>
        console.log("Encountered error during lend requeest: ", err)
      );
  };
  const sendDeleteRequest = (lists) => {
    const newList = [];
    lists.forEach((ele) => {
      newList.push({
        userId: userInfo.userId,
        bookId: ele,
      });
    });
    const deletePayload = {
      delete_data: newList,
    };
    axios
      .delete(`${baseUrl}cart/delete-add-to-cart-books`, {
        data: deletePayload,
      })
      .then((response) => {
        setActive(!active);
        console.log("Borrow Books delete request status: ", response.status);
      })
      .catch((err) => console.log(`Error occurred in delete request: ${err}`));
  };
  return (
    <div className={style.commonStyle}>
      <section className={style.layout}>
        <h1 className={style.title}>Book Management System</h1>
      </section>
      <div className={style.layout3}>
        <section className={style.layout1}>
          <ul>
            <button
              onClick={() =>
                navigate("/dashboard", {
                  state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                  replace: true,
                })
              }
            >
              Home
            </button>
            <button
              onClick={() =>
                navigate("/lendCart", {
                  state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                  replace: true,
                })
              }
            >
              Lend
            </button>
            <button
              onClick={() =>
                navigate("/returnCart", {
                  state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                  replace: true,
                })
              }
            >
              Return
            </button>
            <button
              onClick={() =>
                navigate("/borrowHistory", {
                  state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                  replace: true,
                })
              }
            >
              Borrow History
            </button>
          </ul>
        </section>
        <section>
          <table className={style.lendCartCentre}>
            <thead>
              <tr>
                <th scope="col1">Title</th>
                <th scope="col1">Author</th>
                <th scope="col2">Publications</th>
              </tr>
            </thead>
            <tbody>
              {data.map((ele) => {
                return (
                  <tr
                    key={ele.bookId}
                    style={{
                      backgroundColor: selectedList.has(ele.bookId)
                        ? "rgb(139, 161, 87)"
                        : "#d6dcd7",
                    }}
                    onClick={() => {
                      setSelectedList((prevSelected) => {
                        const newSelected = new Set(prevSelected);
                        if (newSelected.has(ele.bookId)) {
                          newSelected.delete(ele.bookId);
                        } else {
                          newSelected.add(ele.bookId);
                        }
                        return newSelected;
                      });
                    }}
                  >
                    <td>{ele.title}</td>
                    <td>{ele.author}</td>
                    <td>{ele.publications}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
          <div className={style.buttonDiv}>
            <button
              className={style.lendCartButton}
              style={{ cursor: selectedList.size > 0 ? "pointer" : "no-drop" }}
              disabled={selectedList.size > 0 ? false : true}
              onClick={() => sendLendRequest(selectedList)}
            >
              Lend Request
            </button>
            <button
              className={style.lendCartButton}
              style={{ cursor: selectedList.size > 0 ? "pointer" : "no-drop" }}
              disabled={selectedList.size > 0 ? false : true}
              onClick={() => sendDeleteRequest(selectedList)}
            >
              Delete
            </button>
          </div>
        </section>
        <section className={style.layout2}>
          <div className={style.container}>
            <ul>
              <button
                onClick={() =>
                  navigate("/account", {
                    state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                    replace: true,
                  })
                }
              >
                Account
              </button>
              <button
                onClick={() =>
                  navigate("/notifications", {
                    state: { isLoggedIn: props.isLoggedIn, userInfo: userInfo },
                    replace: true,
                  })
                }
              >
                Notifications
              </button>
              <button
                onClick={() =>
                  navigate("/", {
                    replace: true,
                    state: {},
                  })
                }
              >
                Logout
              </button>
            </ul>
          </div>
        </section>
      </div>
    </div>
  );
}

export default LendCart;
