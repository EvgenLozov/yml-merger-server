<p><a href="#configs" class="js-list-configs">
    Список конфигов</a></p>

<h3>Конфиг : <%- name %></h3>

<form class="form-horizontal">
    <div class="form-group">
        <label for="config-name" class="col-sm-2 control-label">
            Название:</label>
        <div class="col-sm-6">
            <input id="config-name" name="name" class="form-control"
                   type="text" value="<%= name %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-user" class="col-sm-2 control-label">
            Имя польз.:</label>
        <div class="col-sm-6">
            <input id="config-user" name="user" class="form-control"
                   type="text" value="<%= user %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-psw" class="col-sm-2 control-label">
            Пароль:</label>
        <div class="col-sm-6">
            <input id="config-psw" name="psw" class="form-control"
                   type="password" value="<%= psw %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-encoding" class="col-sm-2 control-label">
            Кодировка:</label>
        <div class="col-sm-6">
            <input id="config-encoding" name="encoding" class="form-control"
                   type="text" value="<%= encoding %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-inputFile" class="col-sm-2 control-label">
            Исх. файл:</label>
        <div class="col-sm-6">
            <input id="config-inputFile" name="inputFile" class="form-control"
                   type="text" value="<%= inputFile %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-inputFileURL" class="col-sm-2 control-label">
            Ссылка на исх.файл:</label>
        <div class="col-sm-6">
            <input id="config-inputFileURL" name="inputFileURL" class="form-control"
                   type="text" value="<%= inputFileURL %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-filesCount" class="col-sm-2 control-label">
            Кол-во результ. файлов:</label>
        <div class="col-sm-6">
            <input id="config-filesCount" name="filesCount" class="form-control"
                   type="text" value="<%= filesCount %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-limitSize" class="col-sm-2 control-label">
            Макс-ый размер файлов-результатов, Кб.:</label>
        <div class="col-sm-6">
            <input id="config-limitSize" name="limitSize" class="form-control"
                   type="text" value="<%= limitSize/1024 %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-time" class="col-sm-2 control-label">
            Время авт. обновления:</label>
        <div class="col-sm-6">
            <input id="config-time" name="time" class="form-control"
                   type="text" value="<%= time %>" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label for="config-removedCategoryId" class="col-sm-2 control-label">
            categoryId для удаленных офферов:</label>
        <div class="col-sm-6">
            <input id="config-removedCategoryId" name="removedCategoryId" class="form-control"
                   type="text" value="<%= removedCategoryId %>" disabled/>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <% if (modifyCategoryId) { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyCategoryId" checked disabled>  Изменять ид категорий
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyCategoryId" disabled>  Изменять ид категорий
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-categoryIdPrefix" class="col-sm-2 control-label">
                    Префикс categoryId:</label>
                <div class="col-sm-6">
                    <input id="config-categoryIdPrefix" name="categoryIdPrefix" class="form-control"
                           type="text" value="<%= categoryIdPrefix %>" disabled/>
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
                            <input type="checkbox" name="modifyDescription" checked disabled> Изменять описание
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyDescription" disabled> Изменять описание
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-template" class="col-sm-2 control-label">
                    Шаблон для вставки в описание:</label>
                <div class="col-sm-6">
                    <textarea id="config-template" name="template" class="form-control" rows="5" disabled><%= template %></textarea>
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
                            <input type="checkbox" name="modifyOfferId" checked disabled> Изменять ид офферов
                        </label>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-6">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="modifyOfferId" disabled> Изменять ид офферов
                        </label>
                    </div>
                </div>
            </div> <% } %>

            <div class="form-group">
                <label for="config-offerIdPrefix" class="col-sm-2 control-label">
                    Префикс offerId:</label>
                <div class="col-sm-6">
                    <input id="config-offerIdPrefix" name="template" class="form-control"
                           type="text" value="<%= offerIdPrefix %>" disabled/>
                </div>
            </div>

        </div>
    </div>


    <div class="form-group">
        <label class="col-sm-2 control-label">Замена слов</label>
        <div class="col-sm-6">
            <table class="table">
                <thead>
                <tr>
                    <th width="70%">Что заменить</th>
                    <th width="30%">На что заменить</th>
                </tr>
                </thead>
                <tbody>
                <% _.each(replaces, function(replace)
                { %>
                <tr>
                    <td> <%= replace.wordsToReplace %> </td>
                    <% if (replace.replacement != "") { %>
                    <td> <%= replace.replacement %></td>
                    <% } else { %>
                    <td> [Пустая строка] </td> <% } %>
                </tr>
                <% }
                ); %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="form-group">
        <label for="config-epochePeriod" class="col-sm-2 control-label">
            Период подмены файлов-результатов, мин.:</label>
        <div class="col-sm-6">
            <input id="config-epochePeriod" name="epochePeriod" class="form-control"
                   type="text" value="<%= epochePeriod / 3600%>" disabled/>
        </div>
    </div>

    <div class="form-group">
        <label for="config-epocheLink" class="col-sm-2 control-label">
            Ссылка файлов-результатов:</label>
        <div class="col-sm-6">
            <a id="config-epocheLink" href="/prices/<%= id %>.xml" target="_blank">/prices/<%= id %>.xml</a>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-10">
            <a href="#configs/<%= id %>/edit" class="btn btn-default btn-small js-edit">
                Редактировать
            </a>
        </div>
    </div>
</form>
