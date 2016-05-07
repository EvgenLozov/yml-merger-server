<td width="50%">
    <%= wordsToReplace %>
</td>
<td width="30%">
    <% if (replacement == "") {
    print("[Пустая строка]")
    } else {
    print(replacement)
    } %>
</td>
<td width="20%">
    <button class="btn btn-danger btn-sm js-delete">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        Удалить
    </button>
</td>
