<td width="50%">
    <% print(_.escape(wordsToReplace)) %>
</td>
<td width="30%">
    <% if (replacement == "") {
            print("[Пустая строка]")
        } else {
            print(_.escape(replacement))
        } %>
</td>
<td width="20%">
    <button class="btn btn-danger btn-sm js-delete">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        Удалить
    </button>
</td>
