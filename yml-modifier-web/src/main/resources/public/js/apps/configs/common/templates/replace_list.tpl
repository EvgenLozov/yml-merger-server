<label class="col-sm-2 control-label">Замена слов</label>
<div class="col-sm-4">
    <table  class="table">
        <tr>
            <td>
                <textarea class="form-control" rows="2" id="wordsToReplace" placeholder="Что заменить"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" class="form-control" id="replacement" placeholder="На что заменить"">
            </td>
        </tr>
        <tr>
            <td>
                <button class="addReplace btn btn-sm btn-info pull-right" type="button">Добавить</button>
            </td>
        </tr>
    </table>
</div>

<div class="col-sm-6" id="replacesDiv">
    <table class="table table-striped" id="replacesTable">
        <!--replaces table content-->
    </table>
</div>

