import React from "react";
import style from "../common.module.css";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function BorrowHistory(props) {
  const navigate = useNavigate();
  const userInfo = props.userInfo;
  let [history, setHistory] = useState([]);
  const userId = userInfo.userId;
  const host = window.APP_CONFIG.API_HOST;
  const baseUrl = host + "/api/";
  const [selectedList, setSelectedList] = useState(new Set());
  const [active, setActive] = useState(false);
  useEffect(() => {
    axios
      .get(`${baseUrl}cart/past-borrowed-books?userId=${userInfo.userId}`)
      .then((response) => {
        console.log(`Response: ${JSON.stringify(response.data.booksList)}`);
        const cartBooksList = response.data.booksList;
        const bookIdList = cartBooksList.map(function name(params) {
          return params.bookId;
        });
        return axios.post(
          `${baseUrl}books/get-book-info`,
          {
            book_id_list: bookIdList,
          },
          { headers: { "Content-Type": "application/json" } }
        );
      })
      .then((response2) => {
        const seen = new Set();
        const uniqueArray = response2.data.booksList.filter((item) => {
          const duplicate = seen.has(item.bookId);
          seen.add(item.bookId);
          return !duplicate;
        });
        setHistory(uniqueArray);
        // const newSet = new Set();
        // uniqueArray.forEach((ele) => {
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

  const sendDeleteRequest = (lists) => {
    const newList = [];
    lists.forEach((element) => {
      newList.push({
        userId: userId,
        bookId: element,
      });
    });
    const payload = {
      delete_data: newList,
    };
    axios
      .delete(`${baseUrl}cart/delete-past-borrowed-books`, { data: payload })
      .then((response) => {
        setActive(!active);
        console.log(`Received response status: ${response.status}`);
      })
      .catch((err) =>
        console.log(
          "Error received during delete borrowed books request: ",
          err
        )
      );
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
              {" "}
              Lend{" "}
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
          <table className={style.borrowHistory}>
            <thead>
              <tr>
                <th scope="col1">Title</th>
                <th scope="col1">Author</th>
                <th scope="col2">Publications</th>
              </tr>
            </thead>
            <tbody>
              {history.map((ele, index) => {
                return (
                  <tr
                    key={index}
                    // style={{
                    //   backgroundColor: "#d6dcd7",
                    // }}
                    style={{
                      backgroundColor: selectedList.has(ele.bookId)
                        ? "rgb(139, 161, 87)"
                        : "#d6dcd7",
                      cursor: "pointer",
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
          <button
            className={style.returnCartButton}
            style={{
              left: "650px",
              cursor: selectedList.size > 0 ? "pointer" : "no-drop",
            }}
            disabled={selectedList.size > 0 ? false : true}
            onClick={() => sendDeleteRequest(selectedList)}
          >
            Delete
          </button>
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
                onClick={() => navigate("/", { state: {}, replace: true })}
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

export default BorrowHistory;
