function r(n){const s=n,e=s?.response?.status;if(e===404||e===501)return!0;const t=s?.message??"";return!s?.response&&(t.includes("404")||t.includes("Network Error"))}export{r as i};
