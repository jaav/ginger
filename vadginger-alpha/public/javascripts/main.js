jQuery(document).ready(function(){
  var props = { autoHeight: false, collapsible:true};
  if(ginger.accordionTab){
    props.active = ginger.accordionTab -1;
  }
  else props.active = false;
	$("#accordion").accordion(props);
  $(".submitbutton").click(function(ev){
    ev.preventDefault();
    g.doSubmit(this);
  });
});

var g = {};
g.doSubmit = function(button){
  var form = button.form;
  form.submit();

}