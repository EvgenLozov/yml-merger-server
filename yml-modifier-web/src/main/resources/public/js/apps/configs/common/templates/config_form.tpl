<form class="form-horizontal">

    <div id="test-region"></div>

    <div class="form-group">
        <label for="config-name" class="col-sm-2 control-label">
            Название:</label>
        <div class="col-sm-6">
            <input id="config-name" name="name" class="form-control"
                   type="text" value="<%= name %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-user" class="col-sm-2 control-label">
            Имя польз.:</label>
        <div class="col-sm-6">
            <input id="config-user" name="user" class="form-control"
                   type="text" value="<%= user %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-psw" class="col-sm-2 control-label">
            Пароль:</label>
        <div class="col-sm-6">
            <input id="config-psw" name="psw" class="form-control"
                   type="password" value="<%= psw %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-encoding" class="col-sm-2 control-label">
            Кодировка:</label>
        <div class="col-sm-6">
            <input id="config-encoding" name="encoding" class="form-control"
                   type="text" value="<%= encoding %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-inputFile" class="col-sm-2 control-label">
            Исх. файл:</label>
        <div class="col-sm-6">
            <input id="config-inputFile" name="inputFile" class="form-control"
                   type="text" value="<%= inputFile %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-inputFileURL" class="col-sm-2 control-label">
            Ссылка на исх.файл:</label>
        <div class="col-sm-6">
            <input id="config-inputFileURL" name="inputFileURL" class="form-control"
                   type="text" value="<%= inputFileURL %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-filesCount" class="col-sm-2 control-label">
            Кол-во результ. файлов:</label>
        <div class="col-sm-6">
            <input id="config-filesCount" name="filesCount" class="form-control"
                   type="text" value="<%= filesCount %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-limitSize" class="col-sm-2 control-label">
            Макс-ый размер файлов-результатов, Кб.:</label>
        <div class="col-sm-6">
            <input id="config-limitSize" name="limitSize" class="form-control"
                   type="text" value="<%= limitSize/1024 %>" />
        </div>
    </div>
    <div class="form-group">
        <label for="config-time" class="col-sm-2 control-label">
            Время авт. обновления:</label>
        <div class="col-sm-6">
            <input id="config-time" name="time" class="form-control"
                   type="text" value="<%= time %>"/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-removedCategoryId" class="col-sm-2 control-label">
            categoryId для удаленных офферов:</label>
        <div class="col-sm-6">
            <input id="config-removedCategoryId" name="removedCategoryId" class="form-control"
                   type="text" value="<%= removedCategoryId %>"/>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <% if (modifyCategoryId) { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyCategoryId" checked>  Изменять ид категорий
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyCategoryId">  Изменять ид категорий
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-categoryIdPrefix" class="col-sm-2 control-label">
                    Префикс categoryId:</label>
                <div class="col-sm-6">
                    <input id="config-categoryIdPrefix" name="categoryIdPrefix" class="form-control"
                           type="text" value="<%= categoryIdPrefix %>"/>
                </div>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-body">

            <% if (modifyDescription) { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyDescription" checked> Изменять описание
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyDescription"> Изменять описание
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-template" class="col-sm-2 control-label">
                    Шаблон для вставки в описание:</label>
                <div class="col-sm-6">
                    <textarea id="config-template" name="template" class="form-control" rows="5"><%= template %></textarea>
                </div>
            </div>

        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-body">

            <% if (modifyOfferId) { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyOfferId" checked> Изменять ид офферов
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyOfferId"> Изменять ид офферов
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-offerIdPrefix" class="col-sm-2 control-label">
                    Префикс offerId:</label>
                <div class="col-sm-6">
                    <input id="config-offerIdPrefix" name="offerIdPrefix" class="form-control"
                           type="text" value="<%= offerIdPrefix %>"/>
                </div>
            </div>

        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-body">
            <div class="form-group" id="replacesView"></div>
        </div>
    </div>

    <div class="form-group">
        <label for="config-epochePeriod" class="col-sm-2 control-label">
            Период подмены файлов-результатов, мин.:</label>
        <div class="col-sm-6">
            <input id="config-epochePeriod" name="epochePeriod" class="form-control"
                   type="text" value="<%= epochePeriod / 3600 %>"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-10">
            <button class="btn btn-default js-submit">Сохранить</button>
            <button class="btn btn-default js-cancel">Отмена</button>
        </div>
    </div>

</form>
