Feature: Network Scenario

  @Regression
  Scenario: Login
    * "https://www.network.com.tr" base uri olarak eklenir
    * "app" dosyasi header olarak eklenir
    * "login" dosyasi body olarak eklenir
    * "/mobile2/mbUserV2/login" servisine "post" metoduyla istek at
    * Response status kodunun 200 degerine esit oldugu kontrol edilir

  @Smoke
  Scenario: DeleteCartItem
    * "https://www.network.com.tr" base uri olarak eklenir
    * "authority" parametresini "www.network.com.tr" value degeriyle headera ekle
    * "accept" parametresini "application/json, text/plain, */*" value degeriyle headera ekle
    * "accept-language" parametresini "en-US,en;q=0.9" value degeriyle headera ekle
    * "cookie" parametresini "_gcl_au=1.1.570912208.1681285497; _fbp=fb.2.1681285498019.776968785; _tt_enable_cookie=1; _ttp=H9sOBWxTejsOEZOfENE43ofxPM6; _ym_uid=1681285506388425761; _ym_d=1681285506; _hjSessionUser_1660719=eyJpZCI6ImNhZTQxNDA0LTUxN2MtNTBjMS1hY2NiLTJjYmE4YWVhMzNmZSIsImNyZWF0ZWQiOjE2ODEyODU1MDUzNDcsImV4aXN0aW5nIjp0cnVlfQ==; wis_l_879=1; wis_l_1471=1; wis_l_748=1; wis_l_1421=1; firstVisitTime=1681393013097; enh_visitor_session=DfjRYvY81G; enh_source=https://www.google.com/; enh_token=6135f4bb9e1814394e86bc0f; g_conv_id=; OptanonAlertBoxClosed=2023-04-13T13:37:13.294Z; __rtbh.lid=%7B%22eventType%22%3A%22lid%22%2C%22id%22%3A%22pf86dd7YfIV1TWIvZlGr%22%7D; wis_s_71499=233093; visid_incap_2817660=IRE6tquaRvS+JYsURZcHehZ4U2QAAAAAQUIPAAAAAACf4ZyAF8Maoq4sPU75MBQS; wis_s_71501=233096; wis_r_220275=1; wis_r_167388=1; wis_r_236413=1; wis_r_167380=1; wis_r_236412=1; wis_r_167379=1; wis_r_236411=1; wis_l_750=1; _sgf_exp=; wis_l_892=1; LastClick7Days=direct; LastClick5Days=direct; UserID=2005937438; FirstClick5Days=direct; _gid=GA1.3.482318444.1685344053; FirstClick7Days=direct; ASP.NET_SessionId=oru41lxerf455rvkewqevejs; Entegral.CookieKey.LanguageID=0; nlbi_2817660=4eyQShnqYh2+iEibLFGQCwAAAAASKke+T0M1cC63B8VK2rrh; _sgf_session_id=-3581615684563632128; _sgf_clicked_banners=%5B%22slider_2023053105483719950.jpg%3APC%20Main%20Slider%3A1%3Ahttps%3A%2F%2Fwww.network.com.tr%2F50-ye-varan-indirim-1881%22%5D; incap_ses_1187_2817660=MfaJT6NhekTYcOwdPRN5EOdKd2QAAAAA4cCedmFsv2YabVv/GCiLaQ==; wis_c_237867=1; _ym_isad=2; _deviceType=d; LastClick1Day=direct; lastVisit=0; incap_ses_1196_2817660=NDmsA/FmLg+qUxmkkgyZEDdxeGQAAAAAr9W5NGq/5MiLU7MkKcjKUA==; wis_i_72164=235591; _hjSession_1660719=eyJpZCI6IjhhMDUxMjNlLTg5Y2UtNDQ4My05MjRkLTc1N2FhNGMyYmJhNCIsImNyZWF0ZWQiOjE2ODU2MTQ5MDYyMDYsImluU2FtcGxlIjpmYWxzZX0=; _hjAbsoluteSessionInProgress=0; __RequestVerificationToken=Pgx85tP9agZ4EPC1DO5qP5V0gchYJBh9H5crbC24ozX-fdhZzstvqLxX0XDDzEG9cVaPxjFaPij-FCZQY7RT6VFkYZZLWqOgVxUdO-1G_s01; wis_i_72072=235210; wis_i_72804=237866; wis_c_237866=1; wis_i_72805=237867; Entegral.CookieKey.CustomerBasketGuid=MmZkNTMyN2EtMThhNy00OGQyLWExMDItZGM1NTM5MWE2ZmFh; Entegral.CookieKey.Customer=2fd5327a-18a7-48d2-a102-dc55391a6faa; Entegral.SessionKey.FavoritedProductIds=%5b%5d; _sgf_user_id=2fd5327a-18a7-48d2-a102-dc55391a6faa; wis_l_749=1; _dn_sid=3bff044e-392e-4123-b16f-6efe20efe632; wis_u=1907a0b9-8a8e-72b4-84ce-1362937f92e7_0_33_2005937438__60; ARRAffinity=ac34b1a047b7a77113a516ef142ac6ff661eaaf7884099e34431a940426d41cc; ARRAffinitySameSite=ac34b1a047b7a77113a516ef142ac6ff661eaaf7884099e34431a940426d41cc; _hjIncludedInSessionSample_1660719=0; sessionCount=60; sessionCount_converter=60; transactionID=AYM-20230601-2339987; __rtbh.uid=%7B%22eventType%22%3A%22uid%22%2C%22id%22%3A%22%22%7D; wis_i_69918=228180; wis_s_71265=232286; wis_i_71265=232286; LastVizitedURLForShopping=/search?searchKey=%C3%A7orap; wis_c_235210=1; wis_lzbid=235210:1; Entegral.SessionKey.RecentlyViewedProduct=[144492,136417,156237,139884,169484,136026,147508,166928,164816]; wis_v=1685614903981_63_home_1; _ga=GA1.3.309098414.1683715501; cto_bundle=ik_Rr19ZVzNwMkdxNk41bVNyaGExNWVmSzNMVFdLSkYlMkYyUnBSckkxJTJCN1ZPaEZXekZoUUFmJTJCQk94U3BpeVNGSFBFd3B0YUZGRWh6cGd3ZGd3ampEbFNEMGxXeWM3N0U5amlrM0IxMEREejlvZUNjMWxYeHRPaHZIVTElMkJXbDlzakVJbSUyQnpvZTdWRUM2ZjZWQ2hyJTJGbm9NVGwzMVElM0QlM0Q; OptanonConsent=isIABGlobal=false&datestamp=Thu+Jun+01+2023+14%3A19%3A52+GMT%2B0300+(GMT%2B03%3A00)&version=6.29.0&hosts=&genVendors=&landingPath=NotLandingPage&groups=C0001%3A1%2CC0004%3A1%2CC0002%3A1%2CC0003%3A1&AwaitingReconsent=false&geolocation=%3B; ShouldResetCookie=False; _ga_MN9S0Z4WEB=GS1.1.1685613949.39.1.1685618509.60.0.0; _dd_s=rum=0&expire=1685619409866" value degeriyle headera ekle
    * "origin" parametresini "https://www.network.com.tr" value degeriyle headera ekle
    * "referer" parametresini "https://www.network.com.tr/cart" value degeriyle headera ekle
    * "sec-ch-ua-mobile" parametresini "?0" value degeriyle headera ekle
    * "sec-fetch-dest" parametresini "empty" value degeriyle headera ekle
    * "sec-fetch-mode" parametresini "cors" value degeriyle headera ekle
    * "sec-fetch-site" parametresini "same-origin" value degeriyle headera ekle
    * "user-agent" parametresini "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36" value degeriyle headera ekle
    * "sec-ch-ua-platform" parametresini "Windows" value degeriyle headera ekle
    * "sec-ch-ua" parametresini '"Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' value degeriyle headera ekle
    * "variantId" parametresini "678943" value degeriyle parametre ekle
    * "/BasketV2/DeleteCartItem/" servisine "post" metoduyla istek at

  @Regression
  Scenario: AddToCart
      * "https://www.network.com.tr" base uri olarak eklenir
      * "app" dosyasi header olarak eklenir
      * "token" parametresini "2FD5327A-18A7-48D2-A102-DC55391A6FAA" value degeriyle headera ekle
      * "quantity" parametresini "1" value degeriyle parametre ekle
      * "VariantID" parametresini "678943" value degeriyle parametre ekle
      * "/mobile2/mbOrder/AddToCart" servisine "get" metoduyla istek at


