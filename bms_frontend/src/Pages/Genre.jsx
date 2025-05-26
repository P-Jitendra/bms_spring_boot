import React from "react";
import style from "../common.module.css";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function Genre(props) {
  const navigate = useNavigate();
  const userInfo = props.userInfo;
  const [data, setData] = useState([]);
  const host = window.APP_CONFIG.API_HOST;
  const baseUrl = host + "/api/";
  const [selectedList, setSelectedList] = useState(new Set());
  const [active, setActive] = useState(false);
  const params = useParams();

  useEffect(() => {
    console.log("Curr Params data: ", params);
    let newParamsId;
    if (params.id === "Mystery") {
      newParamsId = "Mystery/Detective";
    } else if (params.id === "Action" || params.id === "Adventure") {
      newParamsId = "Action and Adventure";
    } else {
      newParamsId = params.id;
    }
    const payload = {
      genre_list: [newParamsId],
    };
    axios
      .post(`${baseUrl}books/from-genre`, payload, {
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        console.log(
          `Curr Genre books Response: ${JSON.stringify(
            response.data.booksList
          )}`
        );
        setData(response.data.booksList);
        setSelectedList(new Set());
      })
      .catch((error) => {
        console.log(error);
      });
  }, [active, params, baseUrl]);

  const addToCartRequest = (list) => {
    const newList = [];
    list.forEach((element) => {
      newList.push({
        bookId: element,
        userId: userInfo.userId,
      });
    });
    const payload = {
      add_to_cart_data: newList,
    };
    console.log(
      `Printing add to cart request payload: ${JSON.stringify(payload)}`
    );
    axios
      .post(`${baseUrl}cart/add-to-cart-books`, payload, {
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        console.log(response.data);
        setActive(!active);
        setSelectedList(new Set());
      })
      .catch((err) =>
        console.log(
          `Error occurred in add to cart request in genre webpage: ${err}`
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
          <button
            className={style.returnCartButton}
            style={{ cursor: selectedList.size > 0 ? "pointer" : "no-drop" }}
            disabled={selectedList.size > 0 ? false : true}
            onClick={() => addToCartRequest(selectedList)}
          >
            Add to Cart Request
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

export default Genre;
